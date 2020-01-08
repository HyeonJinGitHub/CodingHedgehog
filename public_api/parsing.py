# load library
import urllib.request
import requests
import os



outpath = "C:/알약모양/"
if not os.path.isdir(outpath):
    os.makedirs(outpath) 
for i in range(1,225):
    url = 'http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?ServiceKey=gktht8zTVb%2FDamS96F8Huus3gTehnK%2FaCZZYb45LGQfoauMIPsP4oqdDYxs01vtZS5GTLccenEsLL0i86%2FYXEw%3D%3D&pageNo='
    row ='&numOfRows=100'
    response = requests.get(url=url+str(i)+row)
    text = response.text
    for j in range(1,101):
        s_start = text.find('<DRUG_SHAPE>')
        s_end = text.find('</DRUG_SHAPE>')
        shape = text[s_start+12:s_end]
        shape = shape.replace('타원형','oval')
        shape = shape.replace('반원형','semicircular')
        shape = shape.replace('원형','circle')
        shape = shape.replace('삼각형','triangle')
        shape = shape.replace('사각형','square')
        shape = shape.replace('마름모형','rhombus')
        shape = shape.replace('장방형','oblong')
        shape = shape.replace('오각형','pentagon')
        shape = shape.replace('육각형','hexagon')
        shape = shape.replace('팔각형','octagon')
        shape = shape.replace('기타','other')
        i_start = text.find('<ITEM_IMAGE>')
        i_end = text.find('</ITEM_IMAGE>')
        image = text[i_start+12:i_end]
        item = text.find('</item>')
        text = text[item+7:]
        outfile = str((i-1)*100+j)+'_'+shape+'.png'     
        urllib.request.urlretrieve(image, outpath+outfile)        

outpath = "C:/알약색상/"

if not os.path.isdir(outpath):
     os.makedirs(outpath) 

for i in range(70,225):
    url = 'http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?ServiceKey=gktht8zTVb%2FDamS96F8Huus3gTehnK%2FaCZZYb45LGQfoauMIPsP4oqdDYxs01vtZS5GTLccenEsLL0i86%2FYXEw%3D%3D&pageNo='
    row ='&numOfRows=100'
    response = requests.get(url=url+str(i)+row)
    text = response.text
    for j in range(1,101):
        c_start = text.find('<COLOR_CLASS1>')
        c_end = text.find('</COLOR_CLASS1>')
        color = text[c_start+14:c_end]
        color = color.replace(', ','_')
        item = text.find('</item>')
        text2 = text[:item+7]
        if(text2.find('<COLOR_CLASS2>')>0):
            c_start = text2.find('<COLOR_CLASS2>')
            c_end = text2.find('</COLOR_CLASS2>')
            color = color+"_"+text[c_start+14:c_end]
            color = color.replace(', ','')
            color = color.replace('옅은','')
            color = color.replace('진한','')
            color = color.replace('투명','')
        color = color.replace('하양','white')
        color = color.replace('노랑','yellow')
        color = color.replace('주황','orange')
        color = color.replace('분홍','pink')
        color = color.replace('빨강','red')
        color = color.replace('갈색','brown')
        color = color.replace('연두','lightGreen')
        color = color.replace('초록','green')
        color = color.replace('청록','turquoise')
        color = color.replace('파랑','blue')
        color = color.replace('남색','indigo')
        color = color.replace('자주','lightPurple')
        color = color.replace('보라','purple')
        color = color.replace('회색','grey')
        color = color.replace('검정','black')
        color = color.replace('투명','colorless')
        i_start = text.find('<ITEM_IMAGE>')
        i_end = text.find('</ITEM_IMAGE>')
        image = text[i_start+12:i_end]
        text = text[item+7:]
        outfile = str((i-1)*100+j)+'_'+color+'.png'
        urllib.request.urlretrieve(image, outpath+outfile)        
        

