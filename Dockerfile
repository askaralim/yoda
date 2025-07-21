# Multi-stage build for better security and smaller image size
FROM eclipse-temurin:21-jre-alpine AS runtime

# Create a non-root user for security
RUN addgroup -g 1001 -S appgroup && \
    adduser -u 1001 -S appuser -G appgroup

WORKDIR /app

# Copy the JAR file from build stage
COPY build/libs/yoda-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Set the timezone
ENV TZ=Asia/Shanghai

# JVM options for better performance and memory management
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Switch to non-root user
USER appuser

# Run the application with docker profile, ensuring /app/logs and /app/uploads exist
ENTRYPOINT ["sh", "-c", "mkdir -p /app/logs /app/uploads && java $JAVA_OPTS -Dspring.profiles.active=docker -jar app.jar"]
