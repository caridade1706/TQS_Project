# Use a Node.js base image
FROM node:20 AS build
WORKDIR /appUser
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Debug step to list build directory contents
RUN ls /appUser/build

FROM nginx:alpine
COPY --from=build /appUser/build /usr/share/nginx/html

# Custom Nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 81
CMD ["nginx", "-g", "daemon off;"]
