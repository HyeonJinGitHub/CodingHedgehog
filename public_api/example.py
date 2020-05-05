# load library
import urllib.request
import requests
import os

url = 'http://dikweb.health.kr/ajax/drug_info/drug_info_ajax.asp?nsearch=ndetail&drug_code=2018072000002'
response = requests.get(url=url)
print(response.status_code)
text = response.content
text = text.decode('unicode_escape')
print(text)
