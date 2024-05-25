# Use a Node.js base image
FROM node:20 AS build
WORKDIR /appStaff
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
COPY --from=build /appStaff/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3000

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
