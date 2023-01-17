# server

Small "Hello World" server to try things out on.

## Notes

- Leveraging the OTel Java agent, both metrics and traces are picked up and
  exported correctly. This means sending traces via push and exposing metrics
  to Prometheus via Pull. This simply requires attaching the agent to the
  running process; copying the `jar` into the container and adding the right
  JVM anotation.
- In case we want to do it programatically as opposed to leveraging the agent,
  the `clj-otel` library doesn have support for a Prometheus exporter. This
  slightly complicates things because it means we'll have to wire things
  manually in the case of metrics.

## Running locally

Make sure you have [Leiningen](https://leiningen.org/),
[Clojure](https://www.clojure.org/guides/install_clojure) and
[JDK](https://www.clojure.org/guides/install_clojure#java) set up, then simply:

```sh
lein deps
lein run
```

