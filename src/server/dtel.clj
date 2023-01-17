(ns server.dtel
  (:require
   [steffan-westcott.clj-otel.api.trace.http :as trace-http]
   [steffan-westcott.clj-otel.api.trace.span :as span]))

(defn trace-middleware
  "Ring middleware to add HTTP server span support. This middleware can be
  configured to either use existing server spans created by the OpenTelemetry
  instrumentation agent or manually create new server spans (when not using the
  agent)."
  [handler] (trace-http/wrap-server-span handler))

(defn with-span!
  "Starts a new span, sets the current context to the new context containing
  the span and evaluates `body`."
  [opts f] (span/with-span! opts f))

(def add-span-data!
  "Adds data to a span. All data values documented here are optional unless
  otherwise noted as required."
  span/add-span-data!)

;; This is how it would potentially look to initialise OTel programatically
;; However, it still needs some kinks ironed, so commented it for the time being.

; Import the the below:
; [steffan-westcott.clj-otel.sdk.otel-sdk :as sdk]
; [steffan-westcott.clj-otel.resource.resources :as res]
; [steffan-westcott.clj-otel.exporter.otlp.http.trace :as otlp-http-trace]

; (defn init-otel!
;   "Configure and initialise the OpenTelemetry SDK as the global OpenTelemetry
;   instance used by the application. This function should be evaluated before
;   performing any OpenTelemetry API operations such as tracing. This function
;   may be evaluated once only, any attempts to evaluate it more than once will
;   result in error."
;   [service-name otlp-endpoint]
;   (sdk/init-otel-sdk!
;
;    service-name
;
;    {;; The collection of additional resources are merged with the service name
;     ;; to form information about the entity for which telemetry is recorded.
;     ;; Here the additional resources provide information on the host, OS,
;     ;; process and JVM.
;     :resources       [(res/host-resource)
;                       (res/os-resource)
;                       (res/process-resource)
;                       (res/process-runtime-resource)]
;
;     ;; Configuration options for the context propagation, sampling, batching
;     ;; and export of traces. Here we configure export to a local Jaeger server
;     ;; with default options. The exported spans are batched by default.
;     :tracer-provider
;     {:span-processors
;
;      ;; Configure selected span exporter(s). See span exporter docstrings for
;      ;; further configuration options.
;      [{:exporters [;; Export spans using OTLP via HTTP
;                    (otlp-http-trace/span-exporter
;                     {:endpoint otlp-endpoint})]}]}}))
;
; (defn close-otel!
;   "Shut down OpenTelemetry SDK processes. This should be called before the
;   application exits."
;   []
;   (sdk/close-otel-sdk!))
