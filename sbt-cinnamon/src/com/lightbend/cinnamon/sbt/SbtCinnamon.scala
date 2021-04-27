/*
 * Copyright © 2015–2020 Lightbend, Inc. All rights reserved.
 * No information contained herein may be reproduced or transmitted in any form
 * or by any means without the express written permission of Lightbend, Inc.
 */

package com.lightbend.cinnamon.sbt

import sbt._
import sbt.Keys._
import com.lightbend.sbt.javaagent.JavaAgent
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Plugin for adding Cinnamon agent and dependencies to projects.
 */
object CinnamonAgentOnly extends AutoPlugin {

  override def requires = JavaAgent

  import Cinnamon._

  import CinnamonKeys._
  import JavaAgent.JavaAgentKeys._

  val autoImport = CinnamonKeys

  override def projectSettings = Seq(
    cinnamon := false,
    cinnamon in NativePackagerKeys.dist := true,
    javaAgents += JavaAgent(
      name = "Cinnamon",
      module = library.cinnamonAgent,
      scope = JavaAgent.AgentScope(
        compile = (cinnamon in (Compile, compile)).value,
        test = (cinnamon in (Test, test)).value,
        run = (cinnamon in (Compile, run)).value,
        dist = (cinnamon in NativePackagerKeys.dist).value
      ),
      arguments = cinnamonArguments.?.value.getOrElse(cinnamonLogLevel.?.value.map("loglevel=" + _).orNull)
    )
  )
}

/**
 * Plugin for adding Cinnamon agent, dependencies and resolver to projects.
 */
object Cinnamon extends AutoPlugin {

  override def requires = CinnamonAgentOnly

  object CinnamonKeys {
    val cinnamon = settingKey[Boolean]("Whether cinnamon is enabled in different scopes.")
    val cinnamonLogLevel = settingKey[String]("Log level for cinnamon agent (passed via arguments).")
    val cinnamonArguments = settingKey[String]("Arguments to pass to the cinnamon agent.")
    val cinnamonMuteMissingRepoWarning = settingKey[Boolean]("DEPRECATED: Mute the warning about missing commercial repo. Use: cinnamonSuppressRepoWarnings instead.")
    val cinnamonSuppressRepoWarnings = settingKey[Boolean]("Suppress warnings related to commercial repository detection? Defaults to false.")
    val cinnamonMissingRepoWarningShown = settingKey[AtomicBoolean]("Whether the warning about missing commercial repo has been shown already.")
    val cinnamonMissingAuthRepoWarningShown = settingKey[AtomicBoolean]("Whether the warning about new commercial repo auth scheme usage has been shown already.")
  }

  // Copy of sbt-native-packager dist for agent scoping.
  // This is not auto-imported to avoid ambiguous imports.
  object NativePackagerKeys {
    val dist = taskKey[File]("Creates the distribution packages.")
  }

  import CinnamonKeys._
  import JavaAgent.JavaAgentKeys._

  // Convenience accessors for the generated dependency helpers.
  // The Cinnamon plugin object is auto-imported, allowing
  // `Cinnamon.library` to be used without extra imports.
  val library = CinnamonLibrary

  override def buildSettings = Seq(
    cinnamonMissingRepoWarningShown := new AtomicBoolean(false),
    cinnamonMissingAuthRepoWarningShown := new AtomicBoolean(false)
  )

  private def isLightbendResolver(repository: String): Boolean =
    repository.contains("lightbend") && repository.contains("commercial-releases")

  private def isAuthedLightbendResolver(repository: String): Boolean =
    isLightbendResolver(repository) && repository.contains("/pass/")

  override def projectSettings = Seq(
    fullResolvers := {
      def warnBanner(lines: String*): Unit = {
        val delim = "* "
        def line(n: Int): String = delim * n

        val log = streams.value.log
        val repeat = (Math.ceil(lines.map(_.length).max / delim.length.doubleValue()) + 2).intValue()

        log.warn(s"${line(repeat)}\n${lines.map(l => s"$delim$l").mkString("\n")}\n${line(repeat)}")
      }

      val resolvers = fullResolvers.value

      val lightbendMavenRepo = resolvers.collect {
        case repo: MavenRepository if isLightbendResolver(repo.root) => repo
      }

      val lightbendAuthedMavenRepo = lightbendMavenRepo.collect {
        case repo: MavenRepository if isAuthedLightbendResolver(repo.root) => repo
      }

      if (cinnamonMuteMissingRepoWarning.?.value.getOrElse(false)) {
        warnBanner("Cinnamon sbt setting 'cinnamonMuteMissingRepoWarning' is specified but DEPRECATED, please specify cinnamonSuppressRepoWarnings instead.")
      }

      val suppressWarnings = cinnamonMuteMissingRepoWarning.?.value.getOrElse(false) || cinnamonSuppressRepoWarnings.?.value.getOrElse(false)

      val showRepoMissing = lightbendMavenRepo.isEmpty && !suppressWarnings
      val showAuthedRepoMissing = lightbendAuthedMavenRepo.isEmpty && !suppressWarnings

      val missingWarningShown = (cinnamonMissingRepoWarningShown in ThisBuild).value
      if (showRepoMissing && missingWarningShown.compareAndSet(false, true)) {
        warnBanner(
          "Lightbend commercial resolver is missing.",
          "",
          "Please refer to the Lightbend Telemetry migration guide for details:",
          "  https://developer.lightbend.com/docs/telemetry/2.14.x/project/migration.html"
        )
      }

      val missingAuthWarningShown = (cinnamonMissingAuthRepoWarningShown in ThisBuild).value
      if (showAuthedRepoMissing && !missingWarningShown.get() && missingAuthWarningShown.compareAndSet(false, true)) {
        warnBanner(
          "Lightbend commercial resolver does not use new URL-based credential mechanism.",
          "",
          "Please refer to the Lightbend Telemetry migration guide for details:",
          "  https://developer.lightbend.com/docs/telemetry/2.14.x/project/migration.html"
        )
      }
      fullResolvers.value
    }
  )
}
