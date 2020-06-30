import cv2
import numpy as np
import pytesseract

image = cv2.imread('test2.jpg')
output = np.zeros((image.shape[0],image.shape[1],3), np.uint8)
gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
threshold = cv2.adaptiveThreshold(gray, 255, cv2.ADAPTIVE_THRESH_MEAN_C  , cv2.THRESH_BINARY, 11, 1)

median = cv2.medianBlur(threshold, 11)
median = cv2.bitwise_not(median)

contours, hierarchy = cv2.findContours(median,cv2.RETR_TREE,cv2.CHAIN_APPROX_NONE)

saved_cont = []
thresh = 200

for contour in contours:
    if cv2.contourArea(contour) > thresh:
        print(cv2.contourArea(contour))
        saved_cont.append(contour)

cv2.drawContours(output, saved_cont,-1,(255,255,255),1)

cv2.imshow('original', gray)
cv2.imshow('threshold', threshold)
cv2.imshow('median', median)
cv2.imshow('contour', output)

#cv2.imwrite("threshold.png", threshold)
#cv2.imwrite("median.png", median)
#cv2.imwrite("output.png", output)
print(pytesseract.image_to_string(threshold, lang='eng', config='--psm 1 -c preserve_interword_spaces=1'))

cv2.waitKey(0)
cv2.destroyAllWindows()