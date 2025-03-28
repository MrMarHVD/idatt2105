# Stage 1: Build Frontend
# Use Node.js 21

# Container will only contain frontend
FROM node:21 AS builder

# Set working directory inside container
WORKDIR /app

# Copy contents for frontend source code directory into container
COPY . .

# Installs Vue dependencies and generates a build
RUN npm install && npm run build

# Stage 2: Serve Frontend with nginx
# Installs Alpine Linux image with nginx to serve files
FROM nginx:alpine
# Copy the built frontend into nginx HTML directory
COPY --from=builder /app/dist /usr/share/nginx/html
# Copy custom nginx config for Vue Router history
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
# Expose port 80 for web
EXPOSE 80
# Run nginx in the foreground to keep container running
CMD ["nginx", "-g", "daemon off;"]