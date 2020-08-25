from google.cloud import automl

# TODO(developer): Uncomment and set the following variables

def shape():
    project_id = "quixotic-sol-281406"
    model_id = "ICN8423774195787235328"
    file_path = "images/result.jpg"

    prediction_client = automl.PredictionServiceClient()

    # Get the full path of the model.
    model_full_id = prediction_client.model_path(
     project_id, "us-central1", model_id
    )

    # Read the file.
    with open(file_path, "rb") as content_file:
        content = content_file.read()

    image = automl.types.Image(image_bytes=content)
    payload = automl.types.ExamplePayload(image=image)

# params is additional domain-specific parameters.
# score_threshold is used to filter the result
# https://cloud.google.com/automl/docs/reference/rpc/google.cloud.automl.v1#predictrequest
    params = {"score_threshold": "0.8"}

    response = prediction_client.predict(model_full_id, payload, params)
   # print("Prediction results:")
    for result in response.payload:
       # print("Predicted class name: {}".format(result.display_name))
       # print(
         #"Predicted class score: {}".format(
         #      result.image_object_detection.score
         #)
        #)
        bounding_box = result.image_object_detection.bounding_box
       # print("Normalized Vertices:")
        #for vertex in bounding_box.normalized_vertices:
         #   print("\tX: {}, Y: {}".format(vertex.x, vertex.y))
        return result.display_name
