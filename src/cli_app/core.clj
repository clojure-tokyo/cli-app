(ns cli-app.core
  (:gen-class)
  (:require [clojure.string :as s]))

(defn translate
  [x]
  (condp re-matches x
    #"[+*-]" (keyword x)
    #"\d+"   (Integer/parseInt x)
    x))

(defn- ensure-parsing
  [coll]
  (if-let [unprocessed (->> coll
                            (drop-while (comp not string?))
                            (s/join " ")
                            not-empty)]
    {:error       "Unexpected symbol"
     :unprocessed unprocessed}
    coll))

(defn parse
  [s]
  (->> (s/split s #"\b")
        (map s/trim)
        (filter (comp not empty?))
        (map translate)
        ensure-parsing
        not-empty))

(defn- error
  [expr]
  (when-let [e (:error expr)]
    (str e " We stopped parsing here: " (:unprocessed expr))))

(defn- next-calculation
  [expr acc res]
  (let [[x & rest] expr]
    (if (keyword? x)
      [rest [] (-> x name symbol resolve (reduce (or res 0) acc))]
      [rest (conj acc x) res])))

(defn- do-calculation
  ([expr]
    (do-calculation expr [] nil))
  ([expr acc res]
    (if expr
      (apply do-calculation (next-calculation expr acc res))
      (or res (last acc)))))

(defn calculate
  [expr]
  (or
    (error expr)
    (do-calculation expr)))

(defn -main
  [& args]
  (->> args
       (s/join " ")
       parse
       calculate
       println))
