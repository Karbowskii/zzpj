#Build
FROM node:13.10.1-alpine3.10 AS build
WORKDIR /usr/src/app
COPY package.json ./
RUN npm install
COPY . .
RUN npm run build

#Run
FROM nginx:1.17.9-alpine
COPY --from=build /usr/src/app/dist /usr/share/nginx/html
