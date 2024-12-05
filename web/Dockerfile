# Use the official Node.js image as the base image
FROM node:18-alpine

# Set the working directory in the container
WORKDIR /app

# Copy package.json and package-lock.json for dependency installation
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the entire application code into the working directory
COPY . .

# Expose the port the app runs on
EXPOSE 3000

# Command to start the React development server
CMD ["npm", "start"]