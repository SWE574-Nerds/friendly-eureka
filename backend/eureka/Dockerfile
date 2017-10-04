FROM python:3.6-alpine
ADD . /code
WORKDIR /code
RUN pip install -r requirements.txt
EXPOSE 8000:8000
CMD ["python", "manage.py", "runserver", "0.0.0.0:8000"]
