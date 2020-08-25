# load library
import requests
import json
import pymysql
import urllib.request
import time
from urllib import parse
#pip install PyMySQL
import xml.etree.ElementTree as elemTree

# MySQL Connection 연결
from numpy import unicode

conn = pymysql.connect(host='localhost', user='root', password='hogwarts',
                       db='medicine_db', charset='utf8')

# Connection 으로부터 Cursor 생성
curs = conn.cursor()

#총 데이터 개수 23389

# 공공 API에서 하나씩 데이터 받아오기
# item_name,print_front,print_back,drug_shape,color_class1,color_class2
# item_name으로 검색
# list
# detail
# 23389
for i in range(22639,23389):
    num = i
    print(num)
    # 공공 API에서 데이터 가져오기
    url = 'http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?ServiceKey=gktht8zTVb%2FDamS96F8Huus3gTehnK%2FaCZZYb45LGQfoauMIPsP4oqdDYxs01vtZS5GTLccenEsLL0i86%2FYXEw%3D%3D&pageNo='+str(num)+'&numOfRows=1'
 #   print(url)
    response = requests.get(url=url)
    text = response.text
    tree = elemTree.fromstring(text)
    item_name = ""
    # item_name,print_front,print_back,drug_shape,color_class1
    for item in tree.findall('./body/items/item'):
        item_name = item.find('ITEM_NAME').text
        print_front = item.find('PRINT_FRONT').text
        if print_front is None:
            print_front = ""
        print_back = item.find('PRINT_BACK').text
        if print_back is None:
            print_back = ""
        drug_shape = item.find('DRUG_SHAPE').text
        if drug_shape is None:
            drug_shape = ""
        color = item.find('COLOR_CLASS1').text
        if color is None:
            color = ""
    item_name = item.find('ITEM_NAME').text
    print(item_name)
    id = item_name.find("(")
    if(id>0):
        drug_name = item_name[:id]
    else:
        drug_name = item_name
    item_name = drug_name.replace("밀리그람", "MG")
    item_name = item_name.replace("밀리그램", "MG")
  #  print(item_name)

    item_name = item_name.encode('unicode_escape')
    item_name = str(item_name).upper()
    item_name = item_name.replace("B'", "")
    item_name = item_name.replace("\\U", "%u")
    item_name = item_name.replace("\\%", "%")
    item_name = item_name.replace("'", "")

   # print(item_name)

    url = 'http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name='+item_name+'&drug_print=&match=include&mark_code=&drug_color=&drug_linef=&drug_lineb=&drug_shape=&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&strP=1&endP=1&nsearch=nsearch'
   # print(url)
    response = urllib.request.urlopen(url)
    byte_data = response.read()
    text = byte_data.decode('utf-8')
    text = text.replace("\/","/")   #\/ 문자열 제거(안해주면 unicode escape에서 오류)
    text = text.encode()
    text = text.decode('unicode_escape')[1:-1]
    text = text.replace("\r\n","")  #엔터값이 있으면 파싱을 못함

    if(text!=""):
        #json parsing
        json_object = json.loads(text)
        drug_code = json_object['drug_code']
        image_code = json_object['imgidfy_code']
        upso_name = json_object['upso_name_kfda']

        #sql
        table = "drug_lists" #테이블명"
        sql = "insert into " + table + "(drug_name,drug_code,imgidfy_code,print_front,print_back,drug_shape,color,upso_name_kfda)"
        sql2 = " values ("+'"' +drug_name+ '","'+ str(drug_code) +'","' + str(image_code) + '","'+ str(print_front) + '","' + str(print_back) + '","'+ drug_shape+ '","'+color+ '","' + upso_name +'");'
    #    print(sql+sql2)
        # SQL문 실행
        curs.execute(sql+sql2)

        url2 = 'http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code='
     #   print(url2)
        response = requests.get(url=url2+drug_code)
        text = response.content.decode()    #디코딩
        text = text.replace("\/","/")   #\/ 문자열 제거(안해주면 unicode escape에서 오류)
        text = text.replace("\\\\","\\")   #\/ 문자열 제거(안해주면 unicode escape에서 오류)
        text = text.encode()
        text = text.decode('unicode_escape')[1:-1]
        #print(text)
        text = text.replace("<br/>"," ")
        text = text.replace("</br>"," ")
        text = text.replace("\r\n","")
        text = text.replace("\t"," ")
        text = text.replace("<P></P>"," ")
        text = text.replace("</P>"," ")
        text = text.replace('"',"'")
        text = text.replace("{'",'{"')
        text = text.replace("':'",'":"')
        text = text.replace("','",'","')
        text = text.replace("'}",'"}')

        '''
        start = 1
        while(start>0):
            start = text.find("<P")
            if(start == -1):
                print("없음")
                break
            end = text.find(">")
            if(end == -1):
                print("없음")
                break
            error = text[start:end+1]
            print(error)
            text = text.replace(error,"")
        '''
        '''
        text = text.replace('<P style="MARGIN-LEFT: 1px; TEXT-INDENT: 0px">',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('<P style="MARGIN-LEFT: 0px; TEXT-INDENT: 0px">',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('<P style="MARGIN-LEFT: 2px; TEXT-INDENT: 0px">',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('<P style="MARGIN-LEFT: 3px; TEXT-INDENT: 0px">',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('<P style="MARGIN-LEFT: 3px; TEXT-INDENT: 0px">',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('<BR style="MARGIN-LEFT: 0px; TEXT-INDENT: 0px"><BR><BR style="MARGIN-LEFT: 0px; TEXT-INDENT: 0px"><BR>',"")    #누가 이런거 넣어뒀냐..
        text = text.replace('"투여금기", "신중투여", "상호작용"',"'투여금기', '신중투여', '상호작용'")    #누가 이런거 넣어뒀냐..
        text = text.replace('“목표 금연일"',"'목표 금연일'")    #누가 이런거 넣어뒀냐..
        '''
        text = text.replace("\n","\\r\\n")  #엔터값이 있으면 파싱을 못함
      #  print(text)
      #  print(text[3620:3700])
        #print(text)
        if(text!=""):
            #json parsing
            json_object = json.loads(text)
            drug_code = json_object['drug_code']
            drug_name = json_object['drug_name']
            drug_enm = json_object['drug_enm']
            cls_name = json_object['cls_name']
            item_ingr_type = json_object['item_ingr_type']
            package = json_object['package']
            drug_box = json_object['drug_box']
            charact = json_object['charact']
            sunb = json_object['sunb']
            effect = json_object['effect']
            dosage = json_object['dosage']
            caution = json_object['caution']
            mediguide = json_object['mediguide']
            stmt = json_object['stmt']
            additives = json_object['additives']

            #sql
            table = "drug_details" #테이블명"
            sql2 = "insert into " + table + ' (drug_code,drug_name,drug_enm,cls_name,item_ingr_type,package,drug_box,charact,sunb,effect,dosage,caution,mediguide,stmt,additives) values ("' + str(drug_code) +'","' + drug_name + '","'+ drug_enm + '","' + cls_name + '","' + item_ingr_type + '","' + package
            sql3 = '","'+drug_box + '","' + charact + '","' + sunb + '","' + effect + '","' + dosage + '","' + caution + '","' + mediguide + '","' + stmt + '","' + additives + '");'
            #print(sql2+sql3)
        else:   #detail 데이터가 없는 경우
            # sql
            table = "drug_details"  # 테이블명"
            sql2 = "insert into " + table + ' (drug_code,drug_name,drug_enm,cls_name,item_ingr_type,package,drug_box,charact,sunb,effect,dosage,caution,mediguide,stmt,additives) values ("' + str(drug_code) +'","' + drug_name + '","","","","'
            sql3 = '","","","","","","","","","");'

        # SQL문 실행
        curs.execute(sql2+sql3)
        conn.commit()

        # 데이타 Fetch
        rows = curs.fetchall()
time.sleep(5)

# Connection 닫기
conn.close()
