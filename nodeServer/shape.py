import sys
import cv2
import numpy as np
from PIL import Image

def image_to_square(x1, y1, x2, y2, x3, y3, x4, y4): #좌표를 정사각형 좌표로 변경(모델에 집어넣으려고)
    width = x2 - x1     #가로길이
    height = y4 - y1    #세로길이

    print("lack : ", (width - height))

    if width > height:              #가로의 길이가 세로의 길이보다 클 때(세로 길이를 늘려야함)
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

print(image_to_square(10, 10, 45, 10, 45, 40, 10, 40))
