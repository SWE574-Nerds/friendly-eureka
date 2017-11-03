FROM node:alpine
ADD . /code
WORKDIR /code
RUN npm install

CMD node_modules/@angular/cli/bin/ng build && node server.js
