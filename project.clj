(defproject naive-xml-reader "0.1.0"
  :description "A naive Clojure library that turns XML into maps."
  :url "https://github.com/magnars/naive-xml-reader"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.clojure/data.zip "0.1.1"]
                 [camel-snake-kebab "0.3.2"]]
  :profiles {:dev {:dependencies [[expectations "2.1.4"]
                                  [flare "0.2.9"]]
                   :plugins [[lein-expectations "0.0.7"]
                             [lein-autoexpect "1.8.0"]]
                   :injections [(require 'flare.expectations)
                                (flare.expectations/install!)]}})
