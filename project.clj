(defproject server "0.1.0-SNAPSHOT"
  :description "Simple server abstraction to try things on"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ring/ring-core "1.8.2"]
                 [ring/ring-jetty-adapter "1.8.2"]
                 [compojure "1.7.0"]
                 [com.github.steffan-westcott/clj-otel-api "0.1.5"]]
  :main ^:skip-aot server.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}}

  ; this is required to start the OTEL agent in the background.
  ; NOTE: increases startup time slightly?
  :jvm-opts ["-javaagent:opentelemetry-javaagent.jar"
             "-Dotel.resource.attributes=service.name=hello-world-service"
             "-Dotel.traces.exporter=jaeger"
             "-Dotel.metrics.exporter=prometheus"
             "-Dotel.exporter.prometheus.port=9464"
             "-Dotel.exporter.prometheus.host=0.0.0.0"])
