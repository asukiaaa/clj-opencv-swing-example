(ns clj-opencv-swing-example.core
  (:import [java.awt.image BufferedImage]
           [javax.swing JFrame JLabel ImageIcon WindowConstants SwingUtilities]
           [org.opencv.core Core Mat CvType]
           [org.opencv.imgcodecs Imgcodecs]
           [org.opencv.imgproc Imgproc])
  (:require [clojure.core.async :as async]))

(def lena (Imgcodecs/imread "img/lena.jpg"))
(def frame (new JFrame))

(defn mat->buffered-image [mat]
  (let [gray? (= (.channels mat) 1)
        type (if gray?
               BufferedImage/TYPE_BYTE_GRAY
               BufferedImage/TYPE_3BYTE_BGR)
        image (new BufferedImage (.width mat) (.height mat) type)
        target-pixels (-> image
                          (.getRaster)
                          (.getDataBuffer)
                          (.getData))
        raster (.getRaster image)
        buffer (byte-array (* (.width mat) (.height mat) (if gray? 1 3)))]
    (prn :gray gray?)
    (.get mat 0 0 buffer)
    (System/arraycopy buffer 0 target-pixels 0 (alength buffer))
    image))

(defn show-mat [frame mat & {:keys [title set-visible close-operation]}]
  (when title
    (.setTitle frame title))
  (when-not (nil? set-visible)
    (.setVisible frame set-visible))
  (-> frame
      (.getContentPane)
      (.removeAll))
  (.setSize frame (.width mat) (.height mat))
  (.add frame (->> (mat->buffered-image mat)
                   (new ImageIcon)
                   (new JLabel)))
  (.revalidate frame)
  (.repaint frame)
  (when close-operation
    (.setDefaultCloseOperation frame close-operation)))

(defn color->gray [mat]
  (let [gray-mat (new Mat)]
    (Imgproc/cvtColor lena gray-mat Imgproc/COLOR_RGB2GRAY)
    gray-mat))

(defn -main []
  (show-mat frame lena
            :title "opencv mat on swing"
            :set-visible true
            :close-operation WindowConstants/EXIT_ON_CLOSE)
  (async/go-loop [seconds 1]
    (async/<! (async/timeout 1000))
    (show-mat frame (if (even? seconds)
                      lena
                      (color->gray lena)))
    (recur (inc seconds))))
