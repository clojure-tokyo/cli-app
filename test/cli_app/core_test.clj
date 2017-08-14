(ns cli-app.core-test
  (:require [clojure.test :refer :all]
            [cli-app.core :refer :all]
            [clojure.java.shell]))

(deftest basic-integration
  (testing "that the command runs"
    (is
      (= (clojure.java.shell/sh "java" "-jar" "target/uberjar/cli-app-0.1.0-SNAPSHOT-standalone.jar" "george")
          { :exit 0,
            :out "Hello george!\n"
            :err "" }))))

