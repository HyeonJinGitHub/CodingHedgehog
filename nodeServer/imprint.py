def detect_text(path):
    """Detects text in the file."""
    from google.cloud import vision
    import io
    client = vision.ImageAnnotatorClient()

    with io.open(path, 'rb') as image_file:
        content = image_file.read()

    image = vision.types.Image(content=content)

    response = client.text_detection(image=image)
    texts = response.text_annotations
    #print('Texts:')
    temp = " "
    #print(texts)
    for text in texts:
        #print('\n"{}"'.format(text.description))
       # vertices = (['({},{})'.format(vertex.x, vertex.y)
        #           for vertex in text.bounding_poly.vertices])
        
        if temp != text.description.replace('\n',''):
            temp += text.description.replace('\n','')
            temp = temp.replace(' ','')
       #print('bounds: {}'.format(','.join(vertices)))
 #       return text.descriptionaa
        #print(temp)
        if temp == "ASE":
            temp = "ASF"
        return temp
   # print(temp)
    return temp
    #if response.error.message:
     #   raise Exception(
      #      '{}\nFor more info on error messages, check: '
       #     'https://cloud.google.com/apis/design/errors'.format(
        #        response.error.message))
#detect_text("images/result.jpg")
