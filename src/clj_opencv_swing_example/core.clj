(ns clj-opencv-swing-example.core
  (:import [java.awt.image BufferedImage]
           [javax.swing JFrame JLabel ImageIcon WindowConstants SwingUtilities]
           [org.opencv.core Core Mat CvType]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc])
  (:require [clojure.core.async :as async]))

(def color-lena-mat (Imgcodecs/imread "img/lena.jpg"))
(def j-frame (new JFrame))

(defn config-j-frame [j-frame & {:keys [title set-visible close-operation width height]}]
  (when title
    (.setTitle j-frame title))
  (when-not (nil? set-visible)
    (.setVisible j-frame set-visible))
  (when close-operation
    (.setDefaultCloseOperation j-frame close-operation))
  (when (and width height)
    (.setSize j-frame width height)))

(defn create-buffered-image [mat]
  (let [gray? (= (.channels mat) 1)
        type (if gray?
               BufferedImage/TYPE_BYTE_GRAY
               BufferedImage/TYPE_3BYTE_BGR)]
    (new BufferedImage (.width mat) (.height mat) type)))

(defn update-buffered-image [buffered-image mat]
  (.get mat 0 0 (-> buffered-image
                    (.getRaster)
                    (.getDataBuffer)
                    (.getData))))

(defn color->gray [mat]
  (let [gray-mat (new Mat)]
    (Imgproc/cvtColor mat gray-mat Imgproc/COLOR_RGB2GRAY)
    gray-mat))

(defn -main []
  (config-j-frame j-frame
                  :title "opencv mat on swing"
                  :set-visible true
                  :close-operation WindowConstants/EXIT_ON_CLOSE
                  :width (.width color-lena-mat) :height (.height color-lena-mat))
  (let [color-buffered-image (create-buffered-image color-lena-mat)
        _ (update-buffered-image color-buffered-image color-lena-mat)
        color-panel (->> color-buffered-image
                        (new ImageIcon)
                        (new JLabel))
        gray-lena-mat (color->gray color-lena-mat)
        gray-buffered-image (create-buffered-image gray-lena-mat)
        _ (update-buffered-image gray-buffered-image gray-lena-mat)
        gray-panel (->> gray-buffered-image
                            (new ImageIcon)
                            (new JLabel))]
    (async/go-loop [seconds 1]
      (async/<! (async/timeout 1000))
      (-> j-frame
          .getContentPane
          .removeAll)
      (.add j-frame (if (even? seconds) color-panel gray-panel))
      (.revalidate j-frame)
      (.repaint j-frame)
      (recur (inc seconds)))))
