/*
 * Copyright © 2015–2019 Lightbend, Inc. All rights reserved.
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
    val cinnamonMuteMissingRepoWarning = settingKey[Boolean]("Mute the warning about missing commercial repo. Unmuted unless set explicitly.")
    val cinnamonMissingRepoWarningShown = settingKey[AtomicBoolean]("Whether the warning about missing commercial repo has been shown already.")
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
  val resolver = CinnamonResolver

  override def buildSettings = Seq(
    cinnamonMissingRepoWarningShown := new AtomicBoolean(false)
  )

  override def projectSettings = Seq(
    fullResolvers := {
      val resolvers = fullResolvers.value
      val lightbendMavenResolver = resolvers collect {
        case repo: MavenRepository if repo.root.contains("lightbend") && repo.root.contains("commercial-releases") => repo
      }
      val muted = cinnamonMuteMissingRepoWarning.?.value.getOrElse(false)
      val show = lightbendMavenResolver.isEmpty && !muted
      val log = streams.value.log
      val targetDirectory = target.value
      val shown = (cinnamonMissingRepoWarningShown in ThisBuild).value
      if (show && shown.compareAndSet(false, true)) {
        log.warn(("* " * 50) + "\n" +
          "* Lightbend commercial resolver is missing. See the Lightbend Telemetry migration guide for details:\n" +
          "* https://developer.lightbend.com/docs/telemetry/2.13.x/project/migration.html\n" +
          ("* " * 50)
        )
      }
      fullResolvers.value
    }
  )
}
