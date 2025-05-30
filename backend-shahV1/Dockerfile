# Etapa de construcción - Usamos una imagen válida de Maven con Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Configuración de encoding y optimización de Maven
ENV MAVEN_OPTS="-Dfile.encoding=UTF-8 -XX:+TieredCompilation -XX:TieredStopAtLevel=1"

# Copiar primero solo los archivos necesarios para las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código fuente, varios cambios han sido realizados
COPY src ./src

# Build con caché optimizada
RUN mvn clean package -DskipTests -Dmaven.test.skip=true

# Etapa de ejecución - Usamos una imagen válida de Java 21
FROM eclipse-temurin:21.0.2_13-jre-jammy
WORKDIR /app

# Copiar el artefacto construido
COPY --from=build /app/target/*.jar app.jar

# Variables de entorno por defecto (serán sobrescritas por Railway)
ENV PORT=8080
ENV MYSQLHOST=localhost
ENV MYSQLPORT=3306
ENV MYSQLDATABASE=db
ENV MYSQLUSER=user
ENV MYSQLPASSWORD=password
ENV JWT_SECRET=defaultSecret
ENV JWT_EXPIRATION=43200000

# Salud de la aplicación (opcional pero recomendado)
HEALTHCHECK --interval=30s --timeout=3s \
  CMD curl -f http://localhost:${PORT}/actuator/health || exit 1

EXPOSE $PORT
ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${PORT}"]