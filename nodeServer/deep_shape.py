from darkflow.net.build import TFNet
import cv2

options = {"model": "../darkflow/cfg/shape-tiny-yolo.cfg", "load":"../darkflow/bin/yolo.weights", "threshold":0.1}
tfnet = TFNet(options)
img = cv2.imread("./images/finish.jpg")
result = tfnet.return_predict(img)
print(result)
