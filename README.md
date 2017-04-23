# clj-opencv-swing-example

A Clojure project to use opencv and swing.

# Usage

## Compile opencv files for java

For ubuntu16.10.

```
sudo apt-install cmake ant openjdk-8-jdk
mkdir gitprojects
cd gitprojects
git clone git@github.com:opencv/opencv.git opencv_source
cd opencv_source
git checkout tags/3.2.0
# git fetch -p # if you cannot find 3.2.0 tag
mkdir build
cd build
export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64/
cmake -D BUILD_SHARED_LIBS=OFF ..
make -j4
```

## Copy opencv files for java to this project

```
cd [this proejct dir]
mkdir lib
# jar file
cp ~/gitprojects/opencv_source/build/bin/opencv-320.jar lib/

# needs so file for linux
# needs dylib file for mac
# needs dll file for windows
cp ~/gitprojects/opencv_source/build/lib/libopencv_java320.so lib/ # for linux
```

## Run
```
cd [this project dir]
lein run
```

# License

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.

# References

- [Install commands example](https://github.com/milq/milq/blob/master/scripts/bash/install-opencv.sh)
- [opencv for java](http://docs.opencv.org/3.0-beta/doc/tutorials/introduction/desktop_java/java_dev_intro.html)
- [play opencv in clojure](http://keens.github.io/blog/2015/06/07/clojuredeopencv3_0totawamureru/)
- [opencv java doc](http://docs.opencv.org/java/3.1.0/)
- [opencv3.2 + clojure](http://qiita.com/woxtu/items/bf39e3d53cbf60396d2c)
- [show opencv mat on swing](http://qiita.com/JackMasaki/items/79b883ca5084d7586008)
- [swing JFrame in clojure](http://hifistar.hatenablog.com/entry/2016/04/10/175525)
- [opencv load image to GUI on java](http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/)
- [how to show imagebuffer to jframe](http://stackoverflow.com/questions/299495/how-to-add-an-image-to-a-jpanel)
- [ImShow-Java-OpenCV](https://github.com/master-atul/ImShow-Java-OpenCV/blob/master/ImShow_JCV/src/com/atul/JavaOpenCV/Imshow.java)
- [content pane remove all](http://stackoverflow.com/questions/9347076/how-to-remove-all-components-from-a-jframe-in-java)