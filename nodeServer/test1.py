import cv2

if __name__=='__main__':
    img = cv2.imread('images/result.jpg')
    img1 = cv2.imread('images/camera.jpg')
    
    cv2.imshow('camera', img)
    cv2.imshow('result',img1)
    cv2.waitKey(0)
    cv2.destroyAllWindows()
    cv2.waitKey(1)
