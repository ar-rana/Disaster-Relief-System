# Disaster-Relief-System

## Overview
This platform streamlines disaster relief provision by connecting the central resources, and affected communities. It focuses on speed, and efficient distribution of resources using complex algorithms and integrates AI to ensure help reaches where it's needed most while also ensuring the safety of the AID provider.

## Tech Stack

### **Frontend:**

- React.js
- Tailwind CSS
- React-Leaflet

### **Backend:**

- Spring Boot
- Firestore
- Redis
- RabbitMQ
- PostgreSQL 
- Spring Data JPA
- STOMP Websockets
- OpenStreetMap
- GraphHopper

## Features

- **Safest path for providers**
- **Efficient assignment of AID requests** 
- **AID request tracking for Users** 
- **AI to classify AID requests**
- **Use advanced heuristic algorithms for shortest and safest path**
- **Establish accountability, provide proof of complete or rejected request**
- **Unified platform for Users, Admins and Providers**

<img width="1548" height="524" alt="image" src="https://github.com/user-attachments/assets/0cd66bb1-3449-4a7d-b091-83f1189519a8" />

##
### **Backend Setup:**

1. Clone the repository:
2. Configure Firestore, Redis, PostgreSQL and Java Mail Sender in `application.properties`.
3. Build and run the Spring Boot application:
   ```sh
   cd server
   mvn clean install
   mvn spring-boot:run
   ```

### **Frontend Setup:**

1. Navigate to the frontend directory:
   ```sh
   cd client
   npm install
   npm run dev
   ```
