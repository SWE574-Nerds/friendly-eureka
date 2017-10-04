FROM python:3.6
ADD . /code
WORKDIR /code
RUN pip install -r requirements.txt

CMD python3 waitdb.py && python3 manage.py migrate && python3 manage.py runserver 0.0.0.0:8000 
