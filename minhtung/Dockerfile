# Giai đoạn build với Maven
FROM maven:3.8.4-openjdk-17 AS build

# Copy mã nguồn và pom.xml vào container
COPY src /minhtung/spring-mvc/src
COPY pom.xml /minhtung/spring-mvc

# Đặt thư mục làm việc
WORKDIR /minhtung/spring-mvc

# Build WAR file và bỏ qua test
RUN mvn clean package -DskipTests

# Giai đoạn chạy ứng dụng với JDK nhẹ
FROM openjdk:17-slim

# Copy WAR file từ giai đoạn build
COPY --from=build /minhtung/spring-mvc/target/*.war /minhtung/spring-mvc/app.war

# Mở port 8080
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "/minhtung/spring-mvc/app.war"]
