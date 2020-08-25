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

#총 데이터 개수만큼 for문 돌려주세요

for i in range(20058,20798):
    num = i
    sql = "select drug_code from drug_lists where idx="+str(i)+";"
    curs.execute(sql)
    drug_code = curs.fetchall()
    print(i)
    print(drug_code[0][0])
    url = 'http://www.health.kr/searchDrug/ajax/ajax_result_drug2.asp?drug_cd='+str(drug_code[0][0])
   # print(url)
    response = urllib.request.urlopen(url)
    byte_data = response.read()
    text = byte_data.decode('utf-8')
    text = text.replace("\/","/")   #\/ 문자열 제거(안해주면 unicode escape에서 오류)
    text = text.encode()
    text = text.decode('unicode_escape')[1:-1]
    text = text.replace("\r\n","")  #엔터값이 있으면 파싱을 못함
   # print(text)
    text = text.replace('"', "'")
    text = text.replace("{'", '{"')
    text = text.replace("':'", '":"')
    text = text.replace("':", '":')
    text = text.replace("','", '","')
    text = text.replace(",'", ',"')
    text = text.replace("'}", '"}')

    start = text.find(',"effect"')
    #print(start)
    text = text[:start]+"}"
    #print(text)
    #print(text[5650:5660])

    if(text!=""):
        json_object = json.loads(text)
        picto_img = json_object['picto_img']
        mediguide = json_object['mediguide']
        medititle = json_object['medititle']
    #    print(picto_img)
    #    print(medititle)
    #    print(mediguide)


        #sql
        table = "guides" #테이블명"
        sql = "insert into " + table + "(drug_code,picto_img,mediguide,medititle)"
        sql2 = " values ("+'"' + str(drug_code[0][0]) +'","'+picto_img+'","'+mediguide + '","'+ medititle + '");'
     #   print(sql+sql2)
        # SQL문 실행
        curs.execute(sql+sql2)

        # SQL문 실행
        #curs.execute(sql2+sql3)
        conn.commit()

        # 데이타 Fetch
        rows = curs.fetchall()

time.sleep(5)

# Connection 닫기
conn.close()
