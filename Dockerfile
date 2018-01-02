FROM postgres
COPY . .
RUN chmod +x create-data.sh
