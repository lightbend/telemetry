package com.lightbend.cinnamon.sbt

import sbt._

object CinnamonLibrary extends Dynamic {
  val cinnamonOrganization: String = "com.lightbend.cinnamon"
  val cinnamonVersion: String = "2.17.3"

  def apply(name: String, cross: Boolean = true): ModuleID = {
    cinnamonOrganization % name % cinnamonVersion cross SbtCrossVersion(cross)
  }

  val cinnamonAgent: ModuleID = apply("cinnamon-agent", cross = false)
  val cinnamonAgentCommon: ModuleID = apply("cinnamon-agent-common", cross = false)
  val cinnamonAkka: ModuleID = apply("cinnamon-akka", cross = true)
  val cinnamonAkkaCluster: ModuleID = apply("cinnamon-akka-cluster", cross = true)
  val cinnamonAkkaClusterSPI: ModuleID = apply("cinnamon-akka-cluster-spi", cross = false)
  val cinnamonAkkaGrpc: ModuleID = apply("cinnamon-akka-grpc", cross = true)
  val cinnamonAkkaHttp: ModuleID = apply("cinnamon-akka-http", cross = true)
  val cinnamonAkkaHttpSPI: ModuleID = apply("cinnamon-akka-http-spi", cross = false)
  val cinnamonAkkaPersistence: ModuleID = apply("cinnamon-akka-persistence", cross = true)
  val cinnamonAkkaPersistenceSPI: ModuleID = apply("cinnamon-akka-persistence-spi", cross = false)
  val cinnamonAkkaProjection: ModuleID = apply("cinnamon-akka-projection", cross = true)
  val cinnamonAkkaSPI: ModuleID = apply("cinnamon-akka-spi", cross = false)
  val cinnamonAkkaStream: ModuleID = apply("cinnamon-akka-stream", cross = true)
  val cinnamonAkkaStreamSPI: ModuleID = apply("cinnamon-akka-stream-spi", cross = false)
  val cinnamonAkkaTyped: ModuleID = apply("cinnamon-akka-typed", cross = true)
  val cinnamonAlpakkaKafkaSPI: ModuleID = apply("cinnamon-alpakka-kafka-spi", cross = false)
  val cinnamonCHMetrics: ModuleID = apply("cinnamon-chmetrics", cross = false)
  val cinnamonCHMetrics3: ModuleID = apply("cinnamon-chmetrics3", cross = false)
  val cinnamonCHMetrics3JvmMetrics: ModuleID = apply("cinnamon-chmetrics3-jvm-metrics", cross = false)
  val cinnamonCHMetricsElasticsearchReporter: ModuleID = apply("cinnamon-chmetrics-elasticsearch-reporter", cross = false)
  val cinnamonCHMetricsHdrHistogram: ModuleID = apply("cinnamon-chmetrics-hdrhistogram", cross = false)
  val cinnamonCHMetricsHttpReporter: ModuleID = apply("cinnamon-chmetrics-http-reporter", cross = true)
  val cinnamonCHMetricsJvmMetrics: ModuleID = apply("cinnamon-chmetrics-jvm-metrics", cross = false)
  val cinnamonCHMetricsStatsDReporter: ModuleID = apply("cinnamon-chmetrics-statsd-reporter", cross = false)
  val cinnamonCommon: ModuleID = apply("cinnamon-common", cross = false)
  val cinnamonContextPropagation: ModuleID = apply("cinnamon-context-propagation", cross = true)
  val cinnamonCore: ModuleID = apply("cinnamon-core", cross = true)
  val cinnamonCoreJava: ModuleID = apply("cinnamon-core-java", cross = false)
  val cinnamonDatadog: ModuleID = apply("cinnamon-datadog", cross = false)
  val cinnamonDatadogSocket: ModuleID = apply("cinnamon-datadog-socket", cross = false)
  val cinnamonHikariCPJmxImporter: ModuleID = apply("cinnamon-jmx-importer-hikaricp", cross = false)
  val cinnamonJava: ModuleID = apply("cinnamon-java", cross = false)
  val cinnamonJavaFutureSPI: ModuleID = apply("cinnamon-java-future-spi", cross = false)
  val cinnamonJmxImporter: ModuleID = apply("cinnamon-jmx-importer", cross = false)
  val cinnamonJvmMetricsProducer: ModuleID = apply("cinnamon-jvm-metrics-producer", cross = false)
  val cinnamonKafkaConsumerJmxImporter: ModuleID = apply("cinnamon-jmx-importer-kafka-consumer", cross = false)
  val cinnamonKafkaProducerJmxImporter: ModuleID = apply("cinnamon-jmx-importer-kafka-producer", cross = false)
  val cinnamonLagom: ModuleID = apply("cinnamon-lagom", cross = true)
  val cinnamonLagomProjection: ModuleID = apply("cinnamon-lagom-projection", cross = true)
  val cinnamonLagomProjectionSPI: ModuleID = apply("cinnamon-lagom-projection-spi", cross = false)
  val cinnamonNewRelic: ModuleID = apply("cinnamon-newrelic", cross = false)
  val cinnamonOpenTracing: ModuleID = apply("cinnamon-opentracing", cross = true)
  val cinnamonOpenTracingDatadog: ModuleID = apply("cinnamon-opentracing-datadog", cross = false)
  val cinnamonOpenTracingJaeger: ModuleID = apply("cinnamon-opentracing-jaeger", cross = false)
  val cinnamonOpenTracingSPI: ModuleID = apply("cinnamon-opentracing-spi", cross = false)
  val cinnamonOpenTracingTracer: ModuleID = apply("cinnamon-opentracing-tracer", cross = false)
  val cinnamonOpenTracingZipkin: ModuleID = apply("cinnamon-opentracing-zipkin", cross = false)
  val cinnamonOpenTracingZipkinKafka: ModuleID = apply("cinnamon-opentracing-zipkin-kafka", cross = false)
  val cinnamonOpenTracingZipkinScribe: ModuleID = apply("cinnamon-opentracing-zipkin-scribe", cross = false)
  val cinnamonPlay: ModuleID = apply("cinnamon-play", cross = true)
  val cinnamonPlaySPI: ModuleID = apply("cinnamon-play-spi", cross = false)
  val cinnamonPrometheus: ModuleID = apply("cinnamon-prometheus", cross = false)
  val cinnamonPrometheusHttpServer: ModuleID = apply("cinnamon-prometheus-httpserver", cross = false)
  val cinnamonScala: ModuleID = apply("cinnamon-scala", cross = true)
  val cinnamonScalaFutureSPI: ModuleID = apply("cinnamon-scala-future-spi", cross = false)
  val cinnamonSlf4jEvents: ModuleID = apply("cinnamon-slf4j-events", cross = false)
  val cinnamonSlf4jMdc: ModuleID = apply("cinnamon-slf4j-mdc", cross = true)
  val cinnamonTelegraf: ModuleID = apply("cinnamon-telegraf", cross = false)
  val sbtCinnamon: ModuleID = apply("sbt-cinnamon", cross = true)

  val modules: Seq[ModuleID] = Seq(
    cinnamonAgent,
    cinnamonAgentCommon,
    cinnamonAkka,
    cinnamonAkkaCluster,
    cinnamonAkkaClusterSPI,
    cinnamonAkkaGrpc,
    cinnamonAkkaHttp,
    cinnamonAkkaHttpSPI,
    cinnamonAkkaPersistence,
    cinnamonAkkaPersistenceSPI,
    cinnamonAkkaProjection,
    cinnamonAkkaSPI,
    cinnamonAkkaStream,
    cinnamonAkkaStreamSPI,
    cinnamonAkkaTyped,
    cinnamonAlpakkaKafkaSPI,
    cinnamonCHMetrics,
    cinnamonCHMetrics3,
    cinnamonCHMetrics3JvmMetrics,
    cinnamonCHMetricsElasticsearchReporter,
    cinnamonCHMetricsHdrHistogram,
    cinnamonCHMetricsHttpReporter,
    cinnamonCHMetricsJvmMetrics,
    cinnamonCHMetricsStatsDReporter,
    cinnamonCommon,
    cinnamonContextPropagation,
    cinnamonCore,
    cinnamonCoreJava,
    cinnamonDatadog,
    cinnamonDatadogSocket,
    cinnamonHikariCPJmxImporter,
    cinnamonJava,
    cinnamonJavaFutureSPI,
    cinnamonJmxImporter,
    cinnamonJvmMetricsProducer,
    cinnamonKafkaConsumerJmxImporter,
    cinnamonKafkaProducerJmxImporter,
    cinnamonLagom,
    cinnamonLagomProjection,
    cinnamonLagomProjectionSPI,
    cinnamonNewRelic,
    cinnamonOpenTracing,
    cinnamonOpenTracingDatadog,
    cinnamonOpenTracingJaeger,
    cinnamonOpenTracingSPI,
    cinnamonOpenTracingTracer,
    cinnamonOpenTracingZipkin,
    cinnamonOpenTracingZipkinKafka,
    cinnamonOpenTracingZipkinScribe,
    cinnamonPlay,
    cinnamonPlaySPI,
    cinnamonPrometheus,
    cinnamonPrometheusHttpServer,
    cinnamonScala,
    cinnamonScalaFutureSPI,
    cinnamonSlf4jEvents,
    cinnamonSlf4jMdc,
    cinnamonTelegraf,
    sbtCinnamon
  )

  private def matchable(name: String): String = {
    def stripPrefix(prefix: String, name: String): String =
      if (name startsWith prefix) name.substring(prefix.length) else name
    stripPrefix("cinnamon", name.toLowerCase.replace("-", ""))
  }

  import scala.language.dynamics

  def selectDynamic(name: String): ModuleID = {
    modules find {
      module => matchable(name) == matchable(module.name)
    } getOrElse {
      sys.error(s"Unknown cinnamon module: $name")
    }
  }
}
