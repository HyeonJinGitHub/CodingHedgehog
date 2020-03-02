import sys
import cv2
import numpy as np
from PIL import Image
import time

global IMAGE
global PIXEL_COUNT
global IMG_DATA
global RANGE_Y
global RANGE_X
global DURATION_SUM
global TEST_IMAGE

def colorAverage ():
    r, g, b = 0, 0, 0
    for x in RANGE_X:
        for y in RANGE_Y:
            temp = IMG_DATA[x][y]
            r += temp[0]
            g += temp[1]
            b += temp[2]
    return r/PIXEL_COUNT, g/PIXEL_COUNT, b/PIXEL_COUNT

def order_points(pts):
    rect = np.zeros((4, 2), dtype = "float32")

    s = pts.sum(axis = 1)
    rect[0] = pts[np.argmin(s)]
    rect[2] = pts[np.argmax(s)]

    diff = np.diff(pts, axis = 1)
    rect[1] = pts[np.argmin(diff)]
    rect[3] = pts[np.argmax(diff)]

    return rect
def auto_scan_image():
    TEST_IMAGE = cv2.imread('images/result.jpg')
    orig = TEST_IMAGE.copy()
    r = 800.0 / TEST_IMAGE.shape[0]
    dim = (int(TEST_IMAGE.shape[1] * r), 800)
    TEST_IMAGE = cv2.resize(TEST_IMAGE, dim, interpolation = cv2.INTER_AREA)
    b = 0
    approx = None

    gray = cv2.cvtColor(TEST_IMAGE, cv2.COLOR_BGR2GRAY)
    gray = cv2.GaussianBlur(gray, (3, 3), 0)
    edged = cv2.Canny(gray, 75, 200)

    print("STEP 1: Edge Detection")
    cv2.imwrite("images/STEP1.jpg", edged)

    (cnts, _) = cv2.findContours(edged.copy(), cv2.RETR_LIST, cv2.CHAIN_APPROX_SIMPLE)
    cnts = sorted(cnts, key = cv2.contourArea, reverse = True)[:5]

    for c in cnts:
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.02 * peri, True)
        print('apporx len : ', len(approx))
        if len(approx) > b :
                screenCnt = approx
               # break
        b = max(b, len(approx))
    
    print("STEP 2: Find contours")
    cv2.drawContours(TEST_IMAGE, [screenCnt], -1, (0, 255, 0), 2)
    cv2.imwrite("images/STEP2.jpg", TEST_IMAGE)

    rect = order_points(screenCnt.reshape(4, 2) / r)
    (topLeft, topRight, bottomRight, bottomLeft) = rect

    w1 = abs(bottomRight[0] - bottomLeft[0])
    w2 = abs(topRight[0] - topLeft[0])
    h1 = abs(topRight[1] - bottomRight[1])
    h2 = abs(topLeft[1] - bottomLeft[1])
    maxWidth = max([w1, w2])
    maxHeight = max([h1, h2])

    dst = np.float32([[0,0], [maxWidth-1,0], [maxWidth-1,maxHeight-1], [0,maxHeight-1]])

    M = cv2.getPerspectiveTransform(rect, dst)
    warped = cv2.warpPerspective(orig, M, (maxWidth, maxHeight))

    print("STEP 3: Apply perspective transform")
    cv2.imwrite("images/STEP3.jpg", warped)

if __name__ == "__main__" :
    st = sys.argv[0]
    st1 = int(sys.argv[1])
    st2 = int(sys.argv[2])
    st3 = int(sys.argv[3])
    st4 = int(sys.argv[4])
    st5 = int(sys.argv[5])
    st6 = int(sys.argv[6])
    st7 = int(sys.argv[7])
    st8 = int(sys.argv[8])

    image = cv2.imread('images/camera.jpg')
    
    topLeft = [st1, st2]
    topRight = [st3, st4]
    bottomRight = [st5, st6]
    bottomLeft = [st7, st8]

   # pts1 = np.float32([topLeft, topRight, bottomRight, bottomLeft])

    w1 = abs(bottomRight[0] - bottomLeft[0])
    w2 = abs(topRight[0] - topLeft[0])
    h1 = abs(topRight[1] - bottomRight[1])
    h2 = abs(topLeft[1] - bottomLeft[1])
    minWidth = min([w1, w2])
    minHeight = min([h1, h2])

    #pts2 = np.float32([[0,0], [minWidth-1,0], [minWidth-1,minHeight-1], [0,minHeight-1]])

    #M = cv2.getPerspectiveTransform(pts1, pts2)

    #result = cv2.warpPerspective(image, M, (int(minWidth), int(minHeight)))
    print("시작 x: "+str(st1)+"minWidth: "+str(minWidth)+"시작 y: "+str(st2)+"minHeight: "+str(minHeight));
    src = cv2.cvtColor(image, cv2.IMREAD_COLOR)
    height, width, channel = src.shape
    matrix = cv2.getRotationMatrix2D((width/2, height/2), 180, 1)
   # dst = cv2.warpAffine(src, matrix, (width, height))
    dst = cv2.flip(src, 1)
    img_trim = dst[st1:st1+minWidth, st2:st2+minHeight]
    img_result = cv2.flip(img_trim, 1)
    cv2.imwrite('images/result.jpg', img_result)
    
    auto_scan_image()

    IMAGE = Image.open('images/STEP3.jpg')
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
        print(round(duration, 4), 'seconds')

        DURATION_SUM += duration
    
    DURATION_AVG = DURATION_SUM / 10

    print('avgrage color: rgb(%s, %s, %s)' % (COLOR_AVG))
    print('image size: %s pixel' % PIXEL_COUNT)
    print('average duration: %s seconds' % round(DURATION_AVG, 4))
