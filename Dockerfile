FROM clojure:temurin-17-lein-2.10.0

WORKDIR /app

COPY opentelemetry-javaagent.jar opentelemetry-javaagent.jar

COPY project.clj project.clj
RUN lein deps

COPY src src

# TODO: actually compile a jar and run that instead of relying on build tools.
# Also use multistage.
CMD ["lein", "run"]
