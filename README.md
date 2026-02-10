# MédiLabo Solutions

Application web destinée aux cliniques de santé et cabinets privés pour :

- gérer les informations patients
- gérer les notes des praticiens
- calculer un niveau de risque de diabète (assessment)

## Architecture
Architecture microservices avec Spring Boot, Spring Cloud Gateway, Spring Security (auth basique) et Docker.

- patient-service (SQL / MySQL) : CRUD patient (infos personnelles)
- note-service (NoSQL / MongoDB) : gestion des notes médicales
- assessment-service : calcule le risque en interrogeant patient-service + note-service
- gateway-service : point d’entrée unique (routage vers les microservices)
- front-service : UI (Thymeleaf) qui consomme la gateway

## Prérequis

- Java 21
- Maven
- Docker

## Lancement de l'application avec Docker

1. Créer un fichier .env à la racine du projet, basé sur .env.example
2. Démarrer l'application avec `docker compose up --build`
3. Accéder à l'interface sur : http://localhost:8082

