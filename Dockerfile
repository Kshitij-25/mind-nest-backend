# Base image with Java 17 (adjust if using a different version)
FROM openjdk:17-jdk-slim

# Add metadata (optional but good practice)
LABEL maintainer="kshitijnishu@gmail.com"

# Copy the JAR file from target (assuming the build creates this JAR)
COPY target/*.jar app.jar

# Expose port (Render automatically handles port, but it's good to specify)
EXPOSE 8080

# Start the application
ENTRYPOINT ["java", "-jar", "/app.jar"]


ENV DB_HOST=dpg-cuj1juin91rc73bp3qv0-a
ENV DB_PORT=5432
ENV DB_NAME=mindnest
ENV DB_USER=mindnest_user
ENV DB_PASSWORD=DInkkPSvUezmA6qkuCfwCM8E9FqiOeiQ
