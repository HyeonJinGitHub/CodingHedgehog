# load library
import urllib.request
import requests
import os

outpath = "C:/알약jpg/"
if not os.path.isdir(outpath):
    os.makedirs(outpath) 

url = 'http://www.pharm.or.kr/images/sb_photo/small/'
url2 = "http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name=&drug_print=&match=include&mark_code=&drug_color=&drug_linef=&drug_lineb=&drug_shape=사각형%2C&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&strP=20&endP=1&nsearch=nsearch"
response = requests.get(url=url2)
text = response.text
for i in range(1,20):
    s_start = text.find('imgidfy_code')
    s_end = text.find('print_front')
    image = text[s_start+15:s_end-3]
    rnum = text.find('upso_name_kfda')
    print(rnum)
    text = text[rnum+15:]
    print(image)
    url = 'http://www.pharm.or.kr/images/sb_photo/small/'+image
    outfile = image+'.jpg'     
    urllib.request.urlretrieve(url+"_s.jpg", outpath+outfile)        



