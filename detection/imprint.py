import cv2
import numpy as np
from matplotlib import pyplot as plt

#기본조정
def adjustment(img):
    #GRAY로 변경
    img_gray = cv2.cvtColor(img,cv2.COLOR_BGRA2GRAY)

    ##밝기 조절-어둡게
    M = np.ones(img_gray.shape, dtype = "uint8") * 60
    subtracted = cv2.subtract(img_gray, M)
    #added = cv2.add(img_gray,M)

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
    cv2.imshow("sobel", img_sobel)
    plt.hist(img_sobel.ravel(), 256, [0, 256])
    #임계값 찾기
    x= np.percentile(img_sobel,82)
    #너무 낮으면 노이즈가 생겨서 이부분 수정 필요
    if x <=18 :
        x = 25
    print(x)
    ret, thresh = cv2.threshold(img_sobel,x, 255, cv2.THRESH_BINARY)
    ret, thresh = cv2.threshold(thresh,0, 255, cv2.THRESH_BINARY+cv2.THRESH_OTSU)
    return thresh

#사각형 그리기
def rect(img,thresh):
    #컨투어하기
    contours, hierachy= cv2.findContours(thresh.copy(),cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
    rects = [cv2.boundingRect(each) for each in contours]
    '''sodidity
        for cnt in contours:
        solidity = 0.0
        area = cv2.contourArea(cnt)
        hull = cv2.convexHull(cnt)
        hull_area = cv2.contourArea(hull)
        if(hull_area>0):
            solidity = float(area)/hull_area
        if(solidity>0.3):
            print(cnt)
            contours.remove(cnt)
    '''

    #Aspect Ratio,area
    rects = [(x,y,w,h) for (x,y,w,h) in rects if ((w*h>100)and(w*h<20000)and((float(w)/h)<3))]
    rects.sort()
    #사각형 합치기
    for j in range(0,15):
        for i in range(len(rects)-1) :
            rect = compute_intersect_area(rects[i],rects[i+1])
            if(rect!="out"):
                if(rects[i][2]*rects[i][3]>rects[i+1][2]*rects[i+1][3]):
                    del rects[i+1]
                else:
                    del rects[i]
                rects.append(rect)
        rects.sort()

    #그리기
    for rect in rects:
        # Draw the rectangles
        cv2.rectangle(img, (rect[0], rect[1]),(rect[0] + rect[2], rect[1] + rect[3]), (0, 255, 0), 5)
    return img

#사각형 겹치는 부분이 있으면 합쳐주기
def compute_intersect_area(rect1, rect2):
    x1, y1 = rect1[0], rect1[1]
    x2, y2 = rect1[2], rect1[3]
    x3, y3 = rect2[0], rect2[1]
    x4, y4 = rect2[2], rect2[3]

    if((x1>x3+x4)or(x1+x2<x3)or(y1>y3+y4)or(y1+y2<y3)):
        return "out"
    else:
        left_up_x = min(x1, x3)
        left_up_y = min(y1, y3)
        right_down_x = max(x2, x3+x4)
        right_down_y = max(y2, y3+y4)
        width = right_down_x - left_up_x
        height = right_down_y - left_up_y
        return (left_up_x,left_up_y,width,height)


img = cv2.imread('C:/Users/mayso/test_image2.png')
img = cv2.resize(img, (300, 300))

img_adj = adjustment(img)
img_thresh = binarization(img_adj)
img_result = rect(img,img_thresh)

cv2.imshow("thresh",img_thresh)

cv2.imshow("result",img_result)
plt.show()
cv2.waitKey(0)
cv2.destroyAllWindows()
