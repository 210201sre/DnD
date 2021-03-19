FROM maven:3.6.3-openjdk-8 as builder


# Copy pom.xml and source-code
COPY pom.xml pom.xml
COPY src/ src/

# Build the application
RUN mvn clean package

# Separate stage to save the resulting image size
FROM java:8 as runner
# Expose port 8080 for out web-app
EXPOSE 8080
# Copy the jar file from the previous stage
COPY --from=builder target/dungeons-and-dragons-0.1.jar dungeons-and-dragons.jar
# Runs our program when the container is created
ENTRYPOINT ["java", "-jar", "blockbuster.jar"]