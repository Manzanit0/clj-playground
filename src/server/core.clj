(ns server.core
  (:require
   [server.dtel :as dtel]
   [ring.adapter.jetty :as jetty]
   [compojure.core :refer [GET defroutes]]
   [compojure.route :as route]
   [clojure.tools.logging :as log])
  (:gen-class))

(defn get-hello-world [_req]
  ; this adds an attribute to the current span
  (dtel/add-span-data! {:attributes {:service.hello-world/count 32}})
  ; starts a new sub span
  (dtel/with-span! {:name "print log"}
    (log/info "foo bar baz"))
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body "Hello, World!"})

(defroutes app-routes
  (GET "/" req (get-hello-world req))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> app-routes
      (dtel/trace-middleware)))

(defn -main [& _]
  (log/info "Starting server at http://localhost:3000")
  (jetty/run-jetty app {:port 3000 :join? false}))

