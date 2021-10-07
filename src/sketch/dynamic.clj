(ns sketch.dynamic
  (:require [clojure.pprint :as pretty]
            [quil.core :as quil]))

(def radius 300)

(defn save-frame-to-disk
  ([]
   (quil/save-frame (pretty/cl-format nil
                                      "frames/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-####.jpeg"
                                      (quil/year) (quil/month) (quil/day)
                                      (quil/hour) (quil/minute) (quil/seconds))))
  ([state _]
   (save-frame-to-disk)
   state))

(defn- point-in-circle
  []
  (let [r (* radius
             (quil/random-gaussian))
        t (quil/random 0
                       (* 2 quil/PI))]
    [(* r
        (quil/cos t))
     (* r
        (quil/sin t))]))

(defn- draw-curve
  []
  (quil/stroke 1
               (quil/random 50 100)
               (quil/random 100)
               (quil/random 1))
  (apply quil/curve
         (flatten (take 4
                        (repeatedly point-in-circle)))))

(defn- draw-curves
  [n]
  (dotimes [_ n]
    (draw-curve)))

(defn- draw-point
  []
  (quil/point (rand-int (quil/width))
              (rand-int (quil/height))))

(defn- draw-points
  [n]
  (quil/stroke 0 0 0)
  (dotimes [_ n]
    (draw-point)))

(defn draw
  []
  (quil/no-loop)
  (quil/background 0 0 0)
  (quil/no-fill)
  (quil/with-translation [(* (quil/width)
                             0.75)
                          (* (quil/height)
                             0.75)]
    (draw-curves 666))
  (draw-points 1500000)
  (save-frame-to-disk))

(defn initialise
  []
  (quil/smooth)
  (quil/color-mode :hsb 360 100 100 1.0))
