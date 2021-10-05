(ns sketch.core
  (:require [quil.core :as quil]
            [sketch.dynamic :as dynamic])
  (:gen-class))

(quil/defsketch sketch
  :title "Shaded Circle"
  :setup dynamic/initialise
  :draw dynamic/draw
  #_:features #_[:keep-on-top]
  :size [1300 1300])

(defn refresh
  []
  (use :reload 'sketch.dynamic)
  (.loop sketch))
