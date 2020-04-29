# load library
import urllib.request
import requests
import os

url = 'http://dikweb.health.kr/ajax/idfy_info/idfy_info_ajax.asp?drug_name=&drug_print=H&match=include&mark_code=&drug_color=&drug_linef=&drug_lineb=&drug_shape=&drug_form=&drug_shape_etc=&inner_search=print&inner_keyword=&strP=50&endP=41&nsearch=nsearch'
response = requests.get(url=url)
text = response.content
text = text.decode('unicode_escape')
print(text)

start = text.find('drug_code')
end = text.find('imgidfy')
drug_code = text[start+12:end-3]
print(drug_code)

url2 = 'http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code='
response = requests.get(url=url2+drug_code)
text = response.content
text = text.decode('unicode_escape')
print(text)


        

