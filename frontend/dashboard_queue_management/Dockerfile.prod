# Use a Node.js base image
FROM node:20 AS build
WORKDIR /appDashboard
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
COPY --from=build /appDashboard/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 3002

# Start nginx
CMD ["nginx", "-g", "daemon off;"]
