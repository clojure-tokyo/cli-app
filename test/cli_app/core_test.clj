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

