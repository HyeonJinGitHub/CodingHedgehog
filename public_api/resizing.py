# pip install pillow
import urllib.request
import requests
import os
from os import rename,listdir
from PIL import Image

outpath = "C:/알약모양/"

if not os.path.isdir(outpath):
     os.makedirs(outpath) 
files = listdir(outpath)

for f in files:
     image = Image.open(outpath+f)
     resize = image.resize((32,32))
     resize.save(outpath+f)

    
