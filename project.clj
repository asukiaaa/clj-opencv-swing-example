(defproject clj-opencv-swing-example "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/core.async "0.3.442"]]
  :jvm-opts ["-Djava.library.path=lib"]
  :resource-paths ["lib/opencv-320.jar"]
  :injections [(clojure.lang.RT/loadLibrary org.opencv.core.Core/NATIVE_LIBRARY_NAME)]
  :main clj-opencv-swing-example.core)
