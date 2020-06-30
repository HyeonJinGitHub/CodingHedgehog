import cv2
import numpy as np
from matplotlib import pyplot as plt
import pytesseract
import scipy.fftpack # For FFT2
pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract'


#기본조정
def adjustment(img):
    #GRAY로 변경
    img_gray = cv2.cvtColor(img,cv2.COLOR_BGRA2GRAY)

    ##밝기 조절-어둡게
    M = np.ones(img_gray.shape, dtype = "uint8") * 60
    subtracted = cv2.subtract(img_gray, M)
    added = cv2.add(img_gray,M)

    #가우시안 필터
    img_blur = cv2.GaussianBlur(subtracted, (5, 5), 0)

    return img_blur

#이진화
def binarization(img):
    img_sobel_x = cv2.Sobel(img, cv2.CV_64F, 1, 0, ksize=3)
    img_sobel_x = cv2.convertScaleAbs(img_sobel_x)
    img_sobel_y = cv2.Sobel(img, cv2.CV_64F, 0, 1, ksize=3)
    img_sobel_y = cv2.convertScaleAbs(img_sobel_y)
    img_sobel = cv2.addWeighted(img_sobel_x, 1, img_sobel_y, 1, 0)
    #cv2.imshow("sobel", img_sobel)
    kernel = np.ones((5, 5), np.uint8)
    img_sobel = cv2.morphologyEx(img_sobel,cv2.MORPH_CLOSE,kernel)
    #plt.hist(img_sobel.ravel(), 256, [0, 256])
    #임계값 찾기
    x= np.percentile(img_sobel,82)
    #너무 낮으면 노이즈가 생겨서 이부분 수정 필요
    if x <=18 :
        x = 25
    #print(x)
    ret, thresh = cv2.threshold(img_sobel,x-7, 255, cv2.THRESH_BINARY)
    ret, thresh = cv2.threshold(thresh,0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)
    #cv2.imshow("thresh1", thresh)
    kernel = np.ones((3,3),np.uint8)
    opening = cv2.morphologyEx(thresh,cv2.MORPH_OPEN,kernel)
    #cv2.imshow("opening", opening)
    kernel = np.ones((3,3),np.uint8)
    #closing = cv2.morphologyEx(opening,cv2.MORPH_CLOSE,kernel)
    #closing = cv2.dilate(opening,kernel)
    kernel = np.ones((13,13),np.uint8)
    closing = cv2.morphologyEx(opening,cv2.MORPH_CLOSE,kernel)
    #closing = cv2.morphologyEx(closing,cv2.MORPH_CLOSE,kernel)

    return closing


def run(img):
    img = cv2.resize(img, (900, 300))
    #cv2.imshow("img",img)
    #img_delight = delight(img)
    img_adj = adjustment(img)
    img_thresh = binarization(img_adj)
    #ret,markers = cv2.connectedComponents(img)
    #cv2.imshow("thresh",img_thresh)
    bit = cv2.bitwise_not(img_thresh)
    #cv2.imshow("bit",bit)
    #cv2.imwrite('C:/Users/mayso/result_image.png',bit)

    return bit

img = cv2.imread('C:/Users/mayso/test_image.png')

cv2.imshow("img",img)
cv2.imshow("output",run(img))
print(pytesseract.image_to_string(run(img), lang='eng', config='--psm 1 -c preserve_interword_spaces=1'))



plt.show()
cv2.waitKey(0)
cv2.destroyAllWindows()