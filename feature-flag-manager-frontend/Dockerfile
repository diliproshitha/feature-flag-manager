# build
FROM node:18-alpine as builder
WORKDIR /app
COPY package.json .
# COPY package-lock.json .
RUN npm install
COPY . .
RUN npm run build

# stage 2
FROM nginx:1.25.3-alpine-slim
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
COPY --from=builder /app/build .
ENTRYPOINT [ "nginx", "-g", "daemon off;"]