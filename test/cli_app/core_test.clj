(ns cli-app.core-test
  (:require [clojure.test :refer :all]
            [cli-app.core :refer :all]
            [clojure.java.shell]))

(deftest basic-integration
  (testing "that the command runs"
    (is
      (= (clojure.java.shell/sh "java" "-jar" "target/uberjar/cli-app-0.1.0-SNAPSHOT-standalone.jar" "2 3" "+ 2 *")
          { :exit 0,
            :out "10\n"
            :err "" }))))

(deftest transparency
  (testing "that the same number is returned"
    (are [s expected] (= (calculate s) expected)
      "10"  10)))

(deftest parsing
  (testing "that the parsing works"
    (are [s expected] (= (parse s) expected)
      ""          nil
      "2"         [2]
      "1 10 0 +"  [1 10 0 :+]
      "1 2+ 3 *"  [1 2 :+ 3 :*]
      "0-1+2*3"   [0 :- 1 :+ 2 :* 3])))

(deftest parsing-errors
  (testing "that the parsing properly handles errors"
    (are [s expected] (= (parse s) expected)
      "/"         {:error       "Unexpected symbol"
                   :unprocessed "/"}
      "1 2 / 4 5" {:error       "Unexpected symbol"
                   :unprocessed "/ 4 5"})))
