(ns cli-app.core
  (:gen-class)
  (:require [clojure.string :as s]))

(defn- calculate
  [expr]
  "10")

(defn -main
  [& args]
  (println (calculate (s/join " " args))))
