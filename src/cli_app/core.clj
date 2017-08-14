(ns cli-app.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (str "Hello " (first args) "!")))
