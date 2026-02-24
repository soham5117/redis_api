FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

# Give permission to mvnw
RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 8080

CMD ["java", "-redis", "target/redis-0.0.1-SNAPSHOT.jar"]