# Restaurant Management System - Backend Microservices

This project is a **Microservices-based** backend architecture for a restaurant management system, built using **Java 21**, **Spring Boot 3.4.2**, and **Spring Cloud**.

## 🏗️ System Architecture

The system follows an **Event-Driven Architecture** pattern using Apache Kafka and implements **Service Discovery** via Netflix Eureka.

### Microservices List

| Service | Port | Description | Database |
| :--- | :--- | :--- | :--- |
| **API Gateway** | 8080 | Main entry point & service routing | - |
| **Eureka Server** | 8761 | Service Registry & Discovery | - |
| **Order Service** | 8081 | Order management & event publishing | PostgreSQL + Redis |
| **Notification Service** | 8082 | Real-time notification consumer | MongoDB |
| **Kitchen Service** | 8083 | Kitchen queue & cooking status management | MongoDB |
| **Menu Service** | 8084 | Menu catalog & image asset management | MySQL + MinIO |

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3.4.2, Spring Cloud 2024.0.0
- **Messaging:** Apache Kafka (Confluent)
- **Caching:** Redis
- **Storage:** MinIO (Object Storage)
- **Databases:** PostgreSQL, MySQL, MongoDB
- **Migration:** Flyway (Menu Service)
- **Containerization:** Docker & Docker Compose
- **Architecture:** Clean Architecture (Kitchen & Menu Services)

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Desktop
- Java 21 & Maven (for manual builds)

### Steps to Run
1. **Clone Repository:**
   ```bash
   git clone https://github.com/KopiYama/manajemen-pesanan-backend.git
   cd manajemen-pesanan-backend
   ```

2. **Build All Services:**
   Using the Maven Wrapper in the root (or within each service folder):
   ```bash
   # Manual build example (skipping tests for speed)
   .\mvnw.cmd clean package -DskipTests
   ```

3. **Run Infrastructure & Services:**
   ```bash
   docker-compose up -d --build
   ```

## 📍 Main API Endpoints (via Gateway)

All requests should be routed through the API Gateway at port **8080**:

- **Order API:** `http://localhost:8080/api/orders`
- **Menu API:** `http://localhost:8080/api/menu/menus`
- **Kitchen API:** `http://localhost:8080/api/kitchen`
- **Notification API:** `http://localhost:8080/api/notifications`

## 📊 Monitoring & Console

- **Eureka Dashboard:** `http://localhost:8761`
- **MinIO Console:** `http://localhost:9001` (User/Pass: `minioadmin` / `minioadmin123`)
- **Kafka Broker:** `localhost:29092` (External Access)

## 📝 Important Notes
- Ensure ports 8080-8084, 8761, 3306, 5432, 27017, 9000-9001, and 6379 are available on your local machine.
- Global CORS configuration is set to allow frontend requests from `http://localhost:3000`.
