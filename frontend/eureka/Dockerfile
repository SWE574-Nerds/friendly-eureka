FROM node:alpine
ADD . /code
WORKDIR /code
RUN npm install

EXPOSE 3000
CMD node_modules/@angular/cli/bin/ng build && node server.js
