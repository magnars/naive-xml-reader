(ns naive-xml-reader.core
  (:require [camel-snake-kebab.core :as snake]
            [clojure.data.xml :as xml])
  (:import clojure.data.xml.Element))

(declare simplify-element)

(defn ->val [m ^Element e parent-path list-paths]
  (let [k (snake/->kebab-case-keyword (:tag e))
        path (conj parent-path k)
        v (simplify-element e path list-paths)]
    (if (some #{path} list-paths)
      (update m k (fnil conj []) v)
      (assoc m k v))))

(defn ->map [^Element e path list-paths]
  (reduce #(->val %1 %2 path list-paths) {} (:content e)))

(defn is-string-content? [content]
  (and (string? (first content))
       (not (next content))))

(defn- simplify-element [^Element e path list-paths]
  (let [content (:content e)]
    (when (seq content)
      (if (is-string-content? content)
        (first content)
        (->map e path list-paths)))))

(defn read-xml
  [s & [{:keys [list-paths]}]]
  (-> (xml/parse-str s)
      (simplify-element [] list-paths)))
