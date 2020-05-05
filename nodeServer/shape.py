import cv2 as cv

def setLabel(image, str, contour):
    (text_width, text_height), baseline = cv.getTextSize(str, cv.FONT_HERSHEY_SIMPLEX, 0.7, 1)
    x,y,width,height = cv.boundingRect(contour)
    pt_x = x+int((width-text_width)/2)
    pt_y = y+int((height + text_height)/2)
    cv.rectangle(image, (pt_x, pt_y+baseline), (pt_x+text_width, pt_y-text_height), (200,200,200), cv.FILLED)
    cv.putText(image, str, (pt_x, pt_y), cv.FONT_HERSHEY_SIMPLEX, 0.7, (0,0,0), 1, 8)

def shape():
    img_color = cv.imread('images/finish.png', cv.IMREAD_COLOR)

    img_gray = cv.cvtColor(img_color, cv.COLOR_RGB2GRAY)

    ret,img_binary = cv.threshold(img_gray, 127, 255, cv.THRESH_BINARY_INV|cv.THRESH_OTSU)
    img_binary = cv.bitwise_not(img_binary)
    contours, hierarchy = cv.findContours(img_binary, cv.RETR_CCOMP, cv.CHAIN_APPROX_NONE)
    
    for cnt in contours:
        size = len(cnt)

        epsilon = 0.005 * cv.arcLength(cnt, True)
        approx = cv.approxPolyDP(cnt, epsilon, True)

        size = len(approx)

        cv.line(img_color, tuple(approx[0][0]), tuple(approx[size-1][0]), (0, 255, 0), 3)
        for k in range(size-1):
            cv.line(img_color, tuple(approx[k][0]), tuple(approx[k+1][0]), (0, 255, 0), 3)

        if cv.isContourConvex(approx):
            if size == 3:
                setLabel(img_color, "triangle", cnt)
                ShapeName = "triangle"
           # elif size == 4:
            #    setLabel(img_color, "rectangle", cnt)
             #   ShapeName = "rectangle"
          #  elif size == 5:
           #     setLabel(img_color, "pentagon", cnt)
            #    ShapeName = "pentagon"
           # elif size == 6:
            #    setLabel(img_color, "hexagon", cnt)
             #   ShapeName = "hexagon"
           # elif size == 8:
            #setLabel(img_color, "octagon", cnt)
              #  ShapeName = "octagon"
           # elif size == 10:
            #    setLabel(img_color, "decagon", cnt)
             #   ShapeName = "decagon"
               # print(ShapeName)
           # elif size >= 13 and size <= 20:
            #    setLabel(img_color, "oval", cnt)
            elif size >= 12 and size <=15:
                setLabel(img_color, "oblong", cnt)
                ShapeName = "oblong"
                break
               # print(ShapeName)
            elif size >= 16 and size <=17:
                setLabel(img_color, "circle", cnt)
                ShapeName = "circle"
                break
            elif size >=18 and size <=20:
                setLabel(img_color, "oval", cnt)
                ShapeName = "oval"
                break
           # elif size >= 17 and size <= 20:
            #   setLabel(img_color, "oval", cnt)
             #  ShapeName = "oval"
              # break
            else:
                setLabel(img_color, str(size), cnt)
                ShapeName = "other"
                break
        else:
           # if size >= 13 and size <= 20:
            #    setLabel(img_color, "oval", cnt)
           # else:
           if size >= 12 and size <=15:
               setLabel(img_color, "oblong", cnt)
               ShapeName = "oblong"
               break
           elif size >= 16 and size <= 17:
               setLabel(img_color, "circle", cnt)
               ShapeName = "circle"
               break
           elif size >= 18 and size <=20:
               setLabel(img_color, "oval", cnt)
               ShapeName = "oval"
               break
           else:
               setLabel(img_color, str(size), cnt)
               ShapeName = "other"
               break

    cv.imwrite('images/shape_result.jpg', img_color)
    return ShapeName
