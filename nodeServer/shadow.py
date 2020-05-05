import numpy as np
import cv2
import requests

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

            resize = output[5:int(width)-5, 5:int(height)-5]
            resize = cv2.resize(resize, dsize=(height, width), interpolation=cv2.INTER_CUBIC)

            bit = cv2.bitwise_and(resize, resize, mask=mask_inv)
            bit = cv2.bitwise_not(bit)
            cv2.imwrite("images/edge.jpg", bit) # 엣지 이미지 저장

            # finish = cv2.add(output, empty_img)
            # cv2.imwrite("finish.jpg", finish) # 배경 제거된 이미지 저장

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


# main 함수
# grabcut(20, 25, 190, 190) # 받아온 좌표값 설정 - ix, iy, x, y

def shadow_api():
    response = requests.post(
     'https://api.remove.bg/v1.0/removebg',
     files={'image_file': open('images/result.jpg', 'rb')},
     data={'size': 'auto'},
     headers={'X-Api-Key': 'uKip9X85dNiUDGeMz9rTVE7q'},
    )
    if response.status_code == requests.codes.ok:
        with open('images/finish.png', 'wb') as out:
            out.write(response.content)
    else:
        print("Error:", response.status_code, response.text)



