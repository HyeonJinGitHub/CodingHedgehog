import numpy as np
import cv2
from matplotlib import pyplot as plt

# 입력이미지 
img = cv2.imread('./data/pic0.jpg')
# 마스트 이미지
mask = np.zeros(img.shape[:2],np.uint8)
 
bgdModel = np.zeros((1,65),np.float64)
fgdModel = np.zeros((1,65),np.float64)
 
# Step 1
# 전경 좌표
rect = (600,300,400,200)
cv2.grabCut(img,mask,rect,bgdModel,fgdModel,1,cv2.GC_INIT_WITH_RECT)
 
# Step 2
newmask = cv2.imread('./data/pic0.png',0)
mask[newmask == 0] = 0
mask[newmask == 255] = 1
cv2.grabCut(img,mask,None,bgdModel,fgdModel,1,cv2.GC_INIT_WITH_MASK)
 
#mask2 = np.where((mask==2)|(mask==0),0,1).astype('uint8')
#mask2 = np.where((mask==1) + (mask == 3),255,0).astype('uint8')
#img = img*mask2[:,:,np.newaxis]
mask2 = np.where((mask==1) + (mask == 3),255,0).astype('uint8')
img = cv2.bitwise_and(img,img,mask=mask2)
plt.imshow(img),plt.colorbar(),plt.show()