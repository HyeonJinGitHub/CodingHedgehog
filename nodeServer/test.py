def run_quickstart(file_name):
	import io
	import os

	from google.cloud import vision
	from google.cloud.vision import types

	client = vision.ImageAnnotatorClient()

	with io.open(file_name, 'rb') as image_file:
		content = image_file.read()
	
	image = types.Image(content=content)

	response = client.label_detection(image=image)
	labels = response.label_annotations

	print('Labels:')
	for label in labels:
		print(label.description + " = " + str(int(label.score*100)) + "%")

if __name__ == '__main__':
	run_quickstart("images/result.jpg")

