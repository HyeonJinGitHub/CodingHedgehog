import sys
import cv2
import numpy as np
from PIL import Image
import time
import rgb2colorname

global IMAGE
global PIXEL_COUNT
global IMG_DATA
global RANGE_Y
global RANGE_X
global DURATION_SUM
global TEST_IMAGE
global red
global green
global blue

def colorAverage ():
    global red, green, blue
    r, g, b = 0, 0, 0
    for x in RANGE_X:
        for y in RANGE_Y:
            temp = IMG_DATA[x][y]
            r += temp[0]
            g += temp[1]
            b += temp[2]
    red = round(r/PIXEL_COUNT)
    green = round(g/PIXEL_COUNT)
    blue = round(b/PIXEL_COUNT)
    return round(r/PIXEL_COUNT), round(g/PIXEL_COUNT), round(b/PIXEL_COUNT)

def image_to_square(x1, y1, x2, y2, x3, y3, x4, y4): #좌표를 정사각형 좌표로 변경(모델에 집어넣으려고)
    width = x2 - x1     #가로길이
    height = y4 - y1    #세로길이

    if width > hieght:              #가로의 길이가 세로의 길이보다 클 때(세로 길이를 늘려야함)
        lack = width - height       #세로의 길이와 세로폭 길이 차이 
        if lack % 2 is 0 :                                       #길이 차이가 짝수일 때
            y1, y2 = y1 - (lack/2), y2 - (lack/2)
            y3, y4 = y3 + (lack/2), y4 + (lack/2)
        else :                                                   #길이 차이가 홀수일 때
            y1, y2 = y1 - int(lack/2), y2 - int(lack/2)
            y3, y4 = y3 + int(lack/2) + 1, y4 + int(lack/2)+1
        return x1, y1, x2, y2, x3, y3, x4, y4
    elif width < height:          #세로의 길이가 가로의 길이보다 클 때(가로의 길이를 늘려야 함)
        lack = height - width
        if lack % 2 is 0 :
            x1, x4 = x1 - (lack/2), x4 - (lack/2)
            x2, x3 = x2 + (lack/2), x3 + (lack/2)
        else :
            x1, x4 = x1 - int(lack/2), x4 - int(lack/2)
            x2, x3 = x2 + int(lack/2) + 1, x3 + int(lack/2) + 1
        return x1, y1, x2, y2, x3, y3, x4, y4
    else : return x1, y1, x2, y2, x3, y3, x4, y4                #가로와 세로 길이가 같을 때는 그냥 리턴

if __name__ == "__main__" :
    global red, green, blue
    st = sys.argv[0]
    st1 = int(sys.argv[1])
    st2 = int(sys.argv[2])
    st3 = int(sys.argv[3])
    st4 = int(sys.argv[4])
    st5 = int(sys.argv[5])
    st6 = int(sys.argv[6])
    st7 = int(sys.argv[7])
    st8 = int(sys.argv[8])

    #st1, st2, st3, st4, st5, st6, st7, st8 = image_to_square(st1, st2, st3, st4, st5, st6, st7, st8)

    image = cv2.imread('images/camera.jpg')
    
    topLeft = [st1, st2]
    topRight = [st3, st4]
    bottomRight = [st5, st6]
    bottomLeft = [st7, st8]

    w1 = abs(bottomRight[0] - bottomLeft[0])
    w2 = abs(topRight[0] - topLeft[0])
    h1 = abs(topRight[1] - bottomRight[1])
    h2 = abs(topLeft[1] - bottomLeft[1])
    minWidth = min([w1, w2])
    minHeight = min([h1, h2])

    src = cv2.cvtColor(image, cv2.IMREAD_COLOR)
    height, width, channel = src.shape
    #matrix = cv2.getRotationMatrix2D((width/2, height/2), 180, 1)
   # dst = cv2.warpAffine(src, matrix, (width, height))
    dst = cv2.flip(src, 1)
    img_trim = dst[st1:st1+minWidth, st2:st2+minHeight]
    img_result = cv2.flip(img_trim, 1)
    cv2.imwrite('images/result.jpg', img_result)
    #blur_img = cv2.imread('images/result.jpg')
    #median = cv2.medianBlur(blur_img, 5)
    #cv2.imwrite('images/blur_img.jpg', median)
    color_image = img_result[round(minWidth/4):round(minWidth/4+minWidth/2), round(minHeight/4):round(minHeight/4+minHeight/2)]
    cv2.imwrite('images/color_result.jpg', color_image)

    IMAGE = Image.open('images/color_result.jpg')
    PIXEL_COUNT = IMAGE.size[0] * IMAGE.size[1]
    
    IMG_DATA = IMAGE.load()
    RANGE_Y = range(IMAGE.size[0])
    RANGE_X = range(IMAGE.size[1])
    
    IMG_DATA = [[IMG_DATA[y, x] for y in RANGE_Y] for x in RANGE_X]
   
    DURATION_SUM = 0

    for i in range(0, 10):
        start = time.time()
        COLOR_AVG = colorAverage()
        end = time.time()

        duration = end - start
        DURATION_SUM += duration
    
    DURATION_AVG = DURATION_SUM / 10
    rgb2colorname.color_detect(red, green, blue)
   # print('avgrage color: rgb(%s, %s, %s)' % (COLOR_AVG))

