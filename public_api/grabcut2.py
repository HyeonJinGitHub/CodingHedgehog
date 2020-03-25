import numpy as np
import cv2
from matplotlib import pyplot as plt

Blue,Green,Red,Black,White = (255,0,0),(0,255,0),(0,0,255),(0,0,0),(255,255,255)
DRAW_BG = {'color':Black,'val':0}
DRAW_FG = {'color':White,'val':1}

rect = (0,0,1,1)
drawing = False
rectangle = False
rect_over = False
rect_or_mask = 100
value = DRAW_FG
thickness = 3

def onMouse(event,x,y,flags,params):
    global ix, iy, img, img2, drawing, value, mask,rectangle
    global rect, rect_or_mask,rect_over

    if event == cv2.EVENT_RBUTTONDOWN:
        rectangle = True
        ix,iy = x,y

    elif event == cv2.EVENT_MOUSEMOVE:
        if rectangle:
            img = img2.copy()
            cv2.rectangle(img,(ix,iy),(x,y),Red,2)
            rect = (min(ix,x),min(iy,y),abs(ix-x),abs(iy-y))
            rect_or_mask = 0

    elif event == cv2.EVENT_RBUTTONUP:
        rectangle = False
        rect_over = True

        cv2.rectangle(img,(ix,iy),(x,y),Red,2)
        rect = (min(ix,x),min(iy,y),abs(ix-x),abs(iy-y))
        rect_or_mask = 0
        print('n:적용하기')

    if event == cv2.EVENT_LBUTTONDOWN:
        if not rect_over:
            print('마우스 왼쪽을 누른채로 전경이 되는 부분을 선택하시오')
        else:
            drawing = True
            cv2.circle(img,(x,y),thickness,value['color'],-1)
            cv2.circle(mask,(x,y),thickness,value['val'],-1)

    elif event == cv2.EVENT_MOUSEMOVE:
        if drawing:
            cv2.circle(img,(x,y),thickness,value['color'],-1)
            cv2.circle(mask,(x,y),thickness,value['val'],-1)

    elif event == cv2.EVENT_LBUTTONUP:
        if drawing:
            drawing = False
            cv2.circle(img,(x,y),thickness,value['color'],-1)
            cv2.circle(mask,(x,y),thickness,value['val'],-1)
    return


def grapcut():
    global ix, iy, img, img2, drawing, value, mask,rectangle
    global rect, rect_or_mask,rect_over

    img = cv2.imread('./data/test2.jpg')
    img2 = img.copy()

    mask = np.zeros(img.shape[:2],dtype=np.uint8)
    output = np.zeros(img.shape,np.uint8)

    cv2.namedWindow('input')
    cv2.namedWindow('output')
    #cv2.setMouseCallback('input',onMouse,param=(img,img2))
    cv2.moveWindow('input',img.shape[1]+10,90)

    print('오른쪽 마우스 버튼을 누르고 영역을 지정한 후 n을 누르시오')

    cv2.imshow('output',output)
    cv2.imshow('input',img)

        #k = cv2.waitKey(1) & 0xFF

    bgdModel = np.zeros((1,65),np.float64)
    fgdModel = np.zeros((1,65),np.float64)

    if rect_or_mask == 0:
        cv2.grabCut(img2,mask,rect,bgdModel,fgdModel,1,cv2.GC_INIT_WITH_RECT)
        rect_or_mask = 1
    elif rect_or_mask == 1:
        cv2.grabCut(img2,mask,rect,bgdModel,fgdModel,1,cv2.GC_INIT_WITH_MASK)

    print('0:제거할 배경선택, 1:복원할 전경선택, n:적용하기, r:리셋')

    mask2 = np.where((mask==1) + (mask == 3),255,0).astype('uint8')
    output = cv2.bitwise_and(img2,img2,mask=mask2)

    cv2.destroyAllWindows()

grapcut()