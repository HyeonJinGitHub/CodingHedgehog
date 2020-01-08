# load library
import urllib.request
import requests
import os


outpath = "C:/알약모양테스트/"
if not os.path.isdir(outpath):
     os.makedirs(outpath) 

url = 'https://rximage.nlm.nih.gov/api/rximage/1/rxnav?color=blue&includeMpc=true&includeIngredients=true&rLimit=423&resolution=600'
response = requests.get(url=url)
text = response.text

for i in range(1,423):
    s_start = text.find('shape')
    s_end = text.find('size')
    shape = text[s_start+8:s_end-3]
    shape = shape.replace('CAPSULE','oblong')
    shape = shape.replace('TRIANGLE','triangle')
    shape = shape.replace('OVAL','oval')
    shape = shape.replace('ROUND','circle')
    shape = shape.replace('RECTANGLE','square')
    shape = shape.replace('HEXAGON','hexagon')
    i_start = text.find('imageUrl')
    i_end = text.find('imageSize')
    image = text[i_start+11:i_end-3]
    item = text.find('imageSize')
    text = text[item+9:]
    print(shape)
    outfile = str(i)+'_'+shape+'.jpg'
    urllib.request.urlretrieve(image, outpath+outfile)        
            
        

