(ns sketch.dynamic
  (:require [clojure.pprint :as pretty]
            [quil.core :as quil]))

(def radius 400)

(defn save-frame-to-disk
  ([]
   (quil/save-frame (pretty/cl-format nil
                                      "frames/~d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-~2,'0d-####.jpeg"
                                      (quil/year)
                                      (quil/month)
                                      (quil/day)
                                      (quil/hour)
                                      (quil/minute)
                                      (quil/seconds))))
  ([state _]
   (save-frame-to-disk)
   state))

(defn- point-in-circle
  []
  (let [r (* radius
             (quil/sqrt (quil/random 0 1)))
        t (quil/random 0
                    (* 2 quil/PI))]
    [(* r
        (quil/cos t))
     (* r
        (quil/sin t))]))

(defn- draw-curve
  []
  (quil/stroke 180 9 63 (rand)) ; base1 (grey)
  (apply quil/curve
         (flatten (take 4
                        (repeatedly point-in-circle)))))

(defn- draw-point
  []
  (quil/point (rand-int (quil/width))
              (rand-int (quil/height))))

(defn- draw-points
  [n]
  (quil/stroke 44 10 99)
  (dotimes [_ n]
    (draw-point)))

(defn draw
  []
  (quil/no-loop)
  (quil/background 44 10 99)
  (quil/no-fill)
  (quil/with-translation [(/ (quil/width)
                             2)
                          (/ (quil/height)
                             2)]
    (dotimes [_ 1001]
      (draw-curve)))
  (draw-points 750000)
  (save-frame-to-disk))

(defn initialise
  []
  (quil/smooth)
  (quil/color-mode :hsb 360 100 100 1.0))
