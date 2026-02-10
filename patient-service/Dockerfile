# Image Java runtime
FROM eclipse-temurin:21-jre

# Dossier de travail dans le conteneur
WORKDIR /app

# Copier le jar construit par Maven
COPY target/*.jar app.jar

# Port exposé
EXPOSE 8080

# Lancement
ENTRYPOINT ["java","-jar","app.jar"]
