(ns nitrox.doc.render.structure-test
  (:use midje.sweet)
  (:require [nitrox.doc.render.structure :refer :all]))

^{:refer nitrox.doc.render.structure/inclusive :added "0.1"}
(fact "determines which sections are contained by the other"
  (inclusive :article :section) => true

  (inclusive :chapter :subsection) => true

  (inclusive :chapter :chapter) => false

  (inclusive :section :chapter) => false)

 ^{:refer nitrox.doc.render.structure/seperate :added "0.1"}
(fact "groups elements in an array "
  (seperate #(= 1 %) [1 2 2 1 3 4 5])
  => [[1 2 2] [1 3 4 5]])

^{:refer nitrox.doc.render.structure/containify :added "0.1"}
(fact "makes a nested vector object from a sequence of elements"
  (containify [{:type :generic}
               {:type :paragraph}
               {:type :chapter}
               {:type :paragraph}
               {:type :section}
               {:type :paragraph}
               {:type :subsection}
               {:type :paragraph}
               {:type :section}
               {:type :chapter}
               {:type :section}
               {:type :appendix}])
  => [{:type :article}
      [{:type :generic}
       [{:type :paragraph}]]
      [{:type :chapter}
       [{:type :paragraph}]
       [{:type :section}
        [{:type :paragraph}]
        [{:type :subsection}
         [{:type :paragraph}]]]
       [{:type :section}]]
      [{:type :chapter}
       [{:type :section}]]
      [{:type :appendix}]])

^{:refer nitrox.doc.render.structure/containify :added "0.1"}
(fact "makes a nested vector object from a sequence of elements"
  
  (containify [{:type :generic}
               {:type :paragraph}
               {:type :chapter}
               {:type :paragraph}
               {:type :section}
               {:type :paragraph}
               {:type :subsection}
               {:type :paragraph}
               {:type :section}
               {:type :chapter}
               {:type :section}
               {:type :appendix}]))

^{:refer nitrox.doc.render.structure/structure :added "0.1"}
(fact "creates a nested map structure of elements and their containers"
  (structure [{:type :generic}
              {:type :paragraph}
               {:type :chapter}
               {:type :paragraph}
               {:type :section}
               {:type :paragraph}
               {:type :subsection}
               {:type :paragraph}
               {:type :section}
               {:type :chapter}
               {:type :section}
               {:type :appendix}])
  => {:type :article
      :elements [{:type :generic,
                  :elements [{:type :paragraph}]}
                 {:type :chapter,
                  :elements [{:type :paragraph}
                             {:type :section
                              :elements [{:type :paragraph}
                                         {:type :subsection
                                          :elements [{:type :paragraph}]}]}
                             {:type :section
                              :elements []}]}
                 {:type :chapter
                  :elements [{:type :section
                              :elements []}]}
                 {:type :appendix
                  :elements []}]})
