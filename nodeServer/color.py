import sys
import cv2
import numpy as np
from PIL import Image
import time
import rgb2colorname
import shadow
import shape

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

BLUE, GREEN, RED, BLACK, WHITE = (255, 0, 0), (0, 255, 0), (0, 0, 255), (0, 0, 0), (255, 255, 255)
DRAW_BG = {'color':BLACK, 'val':0}
DRAW_FG = {'color':WHITE, 'val':1}

rect = (0, 0, 1, 1)
drawing = False
rectangle = False
rect_over = False
rect_or_mask = 100
value = DRAW_BG
thickness = 3

def grabcut(ix, iy, x, y):
    global img, img2, drawing, value, mask, rectangle
    global rect, rect_or_mask, rect_over

    img = cv2.imread("images/result.jpg")
    img2 = img.copy()

    width, height = img.shape[:2]
    mask = np.zeros(img.shape[:2], dtype=np.uint8)
    output = np.zeros(img.shape, np.uint8)

    count = 0 # 카운트 설정
    cv2.rectangle(img, (ix, iy), (x, y), RED, 2)
    rect = (min(ix, x), min(iy, y), abs(ix - x), abs(iy - y))
    rect_or_mask = 0

    while True:

        k = ord('n')
        count += 1

        if count > 20: # 20번 수행
            k = 27
            mask_inv = cv2.bitwise_not(mask2)
            empty_img = 255 * np.ones(shape=(width, height, 3), dtype=np.uint8) # 빈 이미지 생성
            empty_img = cv2.bitwise_and(empty_img, empty_img, mask=mask_inv)
            finish = cv2.add(output, empty_img)
            cv2.imwrite("finish.jpg", finish) # 이미지 저장

        if k == 27:
            break

        elif k == ord('n'): # 적용하기
            bgdModel = np.zeros((1, 65), np.float64)
            fgcModel = np.zeros((1, 65), np.float64)

            if rect_or_mask == 0:
                cv2.grabCut(img2, mask, rect, bgdModel, fgcModel, 1, cv2.GC_INIT_WITH_RECT)
                rect_or_mask = 1
            elif rect_or_mask == 1:
                cv2.grabCut(img2, mask, rect, bgdModel, fgcModel, 1, cv2.GC_INIT_WITH_MASK)

        mask2 = np.where((mask==1) + (mask==3), 255, 0).astype('uint8')
        output = cv2.bitwise_and(img2, img2, mask=mask2)

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
    shadow.shadow_api()
    color_img = cv2.imread('images/finish.png', cv2.IMREAD_UNCHANGED)
    #blur_img = cv2.imread('images/result.jpg')
    #median = cv2.medianBlur(blur_img, 5)
    #cv2.imwrite('images/blur_img.jpg', median)
    color_image = color_img[round(minWidth/4):round(minWidth/4+minWidth/2), round(minHeight/4):round(minHeight/4+minHeight/2)]
    cv2.imwrite('images/color_result.png', color_image)
    
    IMAGE = Image.open('images/color_result.png')
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
    find_color = rgb2colorname.color_detect(red, green, blue)
    
    co_width, co_height = color_img.shape[:2]
    center_x = int(co_height / 2.0)
    center_y = int(co_width / 2.0)
    font = cv2.FONT_ITALIC
    (text_width, text_height), baseline = cv2.getTextSize(find_color, cv2.FONT_HERSHEY_SIMPLEX, 0.7, 1)
    location = (center_x-30, center_y + int(text_height/2))
   # cv2.rectangle(color_img, (center_x-50, center_y+baseline), (center_x+text_width-50, center_y-text_height), (200,200,200), cv2.FILLED)
    cv2.putText(color_img, find_color, location, cv2.FONT_HERSHEY_SIMPLEX, 0.7, (255,0,0), 1, 8)
    cv2.imwrite('images/color_result2.png', color_img)
   # print('avgrage color: rgb(%s, %s, %s)' % (COLOR_AVG))
    #print(round(minWidth/8), round(minHeight/4), minWidth, minHeight)
   
    img = cv2.imread("images/result.jpg")
    tmp = img
    #shadow.shadow_api()
    rs_width, rs_height = img.shape[:2]
    shadow.grabcut(5, 10, rs_height-10, rs_width-5)
    # print("width : ", rs_width-5, "height : ", rs_height-10) 
    find_shape = shape.shape()
    print(find_color+', '+find_shape)

