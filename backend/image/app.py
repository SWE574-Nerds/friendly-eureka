from PIL import Image

import random
import string
import flask
import os
import io
import re

from flask import send_file

app = flask.Flask(__name__)
app.config["UPLOAD_FOLDER"] = "./"
app.config["ALLOWED_IMG_EXTENSIONS"] = ('.png', '.jpeg', '.jpg', '.svg', '.gif', '.bmp')

def get_filename(ext):
    res = []
    for i in range(20):
        res.append(random.choice(string.ascii_letters))
    return ''.join(res) + ext


def get_extension(filename):
    return re.sub('^.+/.', '', filename)

@app.route('/', methods=["POST"])
def post_img():
    request = flask.request
    file = request.files['file']
    if not file.filename:
        return "olmadi yar"
    name, ext = os.path.splitext(file.filename)
    ext = ext.lower()
    if ext not in app.config['ALLOWED_IMG_EXTENSIONS']:
        return "normal insanlarin kullandigi formatlari kullanirsak..."
    filename = get_filename(ext)
    data = file.read()
    with open(os.path.join(app.config['UPLOAD_FOLDER'], 'o', filename), "wb") as fd:
        fd.write(data)
    data = io.BytesIO(data)
    image = Image.open(data)
    max_width, max_height = (500, 500)
    width, height = image.size
    if width > max_width or height > max_height:
        width_ratio = max_width / width
        height_ratio = max_height / height
        ratio = min(width_ratio, height_ratio)
        height = height * ratio
        width = width * ratio
        try:
            image = image.resize((int(width), int(height)))
        except Exception as e:
            pass
    return filename;

@app.route('/o/<filename>', methods=['GET'])
def o(filename):
    return send_file('o/'+filename, mimetype='image/' + get_extension(filename))

if __name__ == "__main__":
    app.run(debug=False)
