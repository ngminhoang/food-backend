# Sử dụng image chính thức của OpenJDK làm base image
FROM openjdk:17-jdk-slim

# Cài đặt thông tin ứng dụng
ARG JAR_FILE=target/*.jar

# Sao chép file JAR vào container
COPY ${JAR_FILE} app.jar

# Cấu hình cổng mà ứng dụng sẽ chạy
EXPOSE 8080

# Lệnh để chạy ứng dụng Spring Boot
ENTRYPOINT ["java", "-jar", "/app.jar"]
