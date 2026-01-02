# Cabinet Médical - Architecture SOA avec Microservices

Système de gestion de cabinet médical basé sur une architecture orientée services (SOA) utilisant Spring Boot 4.0.1 et Apache Camel ESB.

---

## 📋 Description du Projet

Ce projet implémente un système de gestion de cabinet médical selon une architecture SOA avec les services suivants:
- **Patient Service**: Gestion des patients
- **Médecin Service**: Gestion des médecins
- **Rendez-vous Service**: Gestion des rendez-vous médicaux
- **Consultation Service**: Gestion des consultations médicales
- **Cabinet ESB**: Enterprise Service Bus pour le routage et l'orchestration des services

---

## 🏗️ Architecture

### Architecture SOA

Le projet suit une architecture SOA (Service-Oriented Architecture) avec les composants suivants:

**Modules:**
1. **cabinet-repo** - Module partagé contenant les entités JPA et repositories
2. **patient-service** - Microservice de gestion des patients (Port 8082)
3. **medecin-service** - Microservice de gestion des médecins (Port 8083)
4. **rendez-vous-service** - Microservice de gestion des rendez-vous (Port 8084)
5. **consultation-service** - Microservice de gestion des consultations (Port 8085)
6. **cabinet-esb** - ESB Apache Camel pour le routage (Port 8080)

### Principes SOA Appliqués

- **Séparation des préoccupations**: Chaque service gère un domaine métier spécifique
- **Réutilisabilité**: Module partagé (cabinet-repo) pour les entités communes
- **Interopérabilité**: Communication via API REST
- **Découplage**: Chaque service possède sa propre base de données
- **Point d'accès unique**: ESB comme API Gateway

---

## 🛠️ Technologies Utilisées

- **Java**: 17
- **Spring Boot**: 4.0.1
- **Spring Data JPA**: 4.0.1
- **Hibernate ORM**: 7.2.0.Final
- **Apache Camel**: 4.12.0
- **H2 Database**: 2.4.240 (In-Memory)
- **Lombok**: 1.18.42
- **Maven**: 3.9+
- **Jackson**: 3.0.3

---

## 📁 Structure du Projet
```
cabinetMedicalTp2SOA/
│
├── cabinet-repo/               # Module partagé (Entities + Repositories)
│   ├── model/                  # Patient, Medecin, RendezVous, Consultation
│   └── repository/             # JPA Repositories
│
├── patient-service/            # Service de gestion des patients
│   ├── controller/
│   ├── service/
│   └── application.properties  # Port 8082, DB: patientdb
│
├── medecin-service/            # Service de gestion des médecins
│   ├── controller/
│   ├── service/
│   └── application.properties  # Port 8083, DB: medecindb
│
├── rendez-vous-service/        # Service de gestion des rendez-vous
│   ├── controller/
│   ├── service/
│   └── application.properties  # Port 8084, DB: rdvdb
│
├── consultation-service/       # Service de gestion des consultations
│   ├── controller/
│   ├── service/
│   └── application.properties  # Port 8085, DB: consultationdb
│
├── cabinet-esb/                # ESB Apache Camel (API Gateway)
│   ├── routes/                 # Définition des routes Camel
│   └── application.properties  # Port 8080
│
├── screenshots/                # Captures d'écran des tests
│
└── README.md
```

---

## ✅ Prérequis

- **JDK 17** ou supérieur
- **Maven 3.9+**
- **Postman** (pour les tests API)

---

## 📥 Installation et Démarrage

### 1. Build du module partagé
```bash
cd cabinet-repo
mvn clean install -DskipTests
```

### 2. Build des microservices
```bash
# Patient Service
cd patient-service
mvn clean install -DskipTests

# Médecin Service
cd medecin-service
mvn clean install -DskipTests

# Rendez-vous Service
cd rendez-vous-service
mvn clean install -DskipTests

# Consultation Service
cd consultation-service
mvn clean install -DskipTests

# ESB
cd cabinet-esb
mvn clean install -DskipTests
```

### 3. Démarrage des services

**Ordre recommandé:**
```bash
# Terminal 1
cd patient-service
mvn spring-boot:run

# Terminal 2
cd medecin-service
mvn spring-boot:run

# Terminal 3
cd rendez-vous-service
mvn spring-boot:run

# Terminal 4
cd consultation-service
mvn spring-boot:run

# Terminal 5
cd cabinet-esb
mvn spring-boot:run
```

---

## 🌐 Points d'Accès API

### Via ESB (Recommandé - Port 8080)

| Service | Endpoint ESB |
|---------|--------------|
| Patients | `http://localhost:8080/api/patients` |
| Médecins | `http://localhost:8080/api/medecins` |
| Rendez-vous | `http://localhost:8080/api/rendezvous` |
| Consultations | `http://localhost:8080/api/consultations` |

### Accès Direct aux Services (Interne)

| Service | Endpoint Interne | Port |
|---------|------------------|------|
| Patient Service | `/internal/api/v1/patients` | 8082 |
| Médecin Service | `/internal/api/v1/medecins` | 8083 |
| Rendez-vous Service | `/internal/api/v1/rendezvous` | 8084 |
| Consultation Service | `/internal/api/v1/consultations` | 8085 |

---

## 📋 Règles de Gestion Implémentées

### Patient Service
- Nom obligatoire
- Téléphone obligatoire
- Date de naissance obligatoire

### Médecin Service
- Nom obligatoire
- Prénom obligatoire
- Spécialité obligatoire
- Téléphone obligatoire

### Rendez-vous Service
- La date du rendez-vous doit être future
- Le patient doit être fourni (ID)
- Le médecin doit être fourni (ID)
- Statuts autorisés: `PLANIFIE`, `CONFIRME`, `ANNULE`, `TERMINE`
- Statut par défaut: `PLANIFIE`

### Consultation Service
- Le rendez-vous doit être fourni (ID)
- La date de consultation est obligatoire
- Le rapport doit contenir au moins 10 caractères

---

## 🧪 Tests

### Exemples de Requêtes

#### Créer un Patient
```http
POST http://localhost:8082/internal/api/v1//patients
Content-Type: application/json

{
  "nom": "Alami",
  "telephone": "0612345678",
  "dateNaissance": "1990-05-15",
  "adresse": "Tangier, Morocco"
}
```

#### Créer un Médecin
```http
POST localhost:8083/internal/api/v1//api/medecins
Content-Type: application/json

{
  "nom": "Benjelloun",
  "prenom": "Hassan",
  "specialite": "Cardiologue",
  "telephone": "0698765432",
  "email": "h.benjelloun@clinic.ma"
}
```

#### Créer un Rendez-vous
```http
POST http://localhost:8084/internal/api/v1//rendezvous
Content-Type: application/json

{
  "dateRdv": "2026-01-15T10:00:00",
  "patient": { "id": 1 },
  "medecin": { "id": 1 }
}
```

#### Créer une Consultation
```http
POST http://localhost:8085/internal/api/v1//consultations
Content-Type: application/json

{
  "dateConsultation": "2026-01-15T10:30:00",
  "rapport": "Patient présente des symptômes de grippe. Prescription paracétamol.",
  "rendezVous": { "id": 1 }
}
```

### Captures d'Écran

Toutes les captures d'écran des tests effectués avec Postman sont disponibles dans le dossier **`screenshots/`**.

---

## 🔧 Configuration

### Ports des Services

| Service | Port | Base de Données |
|---------|------|-----------------|
| patient-service | 8082 | H2 (patientdb) |
| medecin-service | 8083 | H2 (medecindb) |
| rendez-vous-service | 8084 | H2 (rdvdb) |
| consultation-service | 8085 | H2 (consultationdb) |
| cabinet-esb | 8080 | - |

### Base de Données H2

Chaque service utilise une base de données H2 en mémoire indépendante. Les données sont réinitialisées à chaque redémarrage.

**Console H2:**
- URL: `http://localhost:<PORT>/h2-console`
- JDBC URL: `jdbc:h2:mem:<dbname>`
- Username: `sa`
- Password: _(vide)_

---

## 🔄 Fonctionnalités Implémentées

### CRUD Complet
- ✅ Create (POST)
- ✅ Read (GET - All & By ID)
- ✅ Update (PUT)
- ✅ Delete (DELETE)

### Fonctionnalités Avancées
- ✅ Validation des règles métier
- ✅ Messages d'erreur personnalisés
- ✅ Routage via ESB Apache Camel
- ✅ Architecture microservices découplée
- ✅ Gestion des statuts (Rendez-vous)
- ✅ Requêtes spécifiques (par patient, par médecin, par rendez-vous)

---

## 🚨 Gestion des Erreurs

Tous les services retournent des messages d'erreur appropriés en cas de validation échouée:

- **400 Bad Request**: Données invalides ou règles métier non respectées
- **404 Not Found**: Ressource introuvable
- **201 Created**: Création réussie
- **204 No Content**: Suppression réussie

---

## 💡 Points Techniques Importants

### Architecture Microservices
- Chaque service possède sa propre base de données (principe de Database per Service)
- Communication via API REST
- Pas de partage direct de base de données entre services

### Module Partagé (cabinet-repo)
- Configuration Maven avec `<skip>true</skip>` pour éviter le repackaging Spring Boot
- Génération d'un JAR normal utilisable comme dépendance
- Contient les entités et repositories communs

### ESB Apache Camel
- Routes configurées pour rediriger les requêtes
- Point d'entrée unique pour tous les services
- Facilite l'ajout de logique métier transversale (logging, sécurité, etc.)

---

## 📞 Auteur

Projet réalisé dans le cadre du TP SOA - Architecture Orientée Services

---

## 📄 Notes

- Les bases de données sont en mémoire (H2) et se réinitialisent à chaque redémarrage
- Les tests sont documentés avec des captures d'écran dans le dossier `screenshots/`
- L'ESB expose une API publique unifiée sur le port 8080
- Les services internes sont accessibles directement pour le debugging (ports 8082-8085)