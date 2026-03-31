# Restaurant Management System - Backend Microservices

Proyek ini adalah arsitektur backend berbasis **Microservices** untuk sistem manajemen restoran, dibangun menggunakan **Java 21**, **Spring Boot 3.4.2**, dan **Spring Cloud**.

## 🏗️ Arsitektur Sistem

Sistem ini menggunakan pola *Event-Driven Architecture* dengan Apache Kafka dan *Service Discovery* menggunakan Netflix Eureka.

### Daftar Layanan (Services)

| Service | Port | Deskripsi | Database |
| :--- | :--- | :--- | :--- |
| **API Gateway** | 8080 | Entry point utama & routing layanan | - |
| **Eureka Server** | 8761 | Service Registry & Discovery | - |
| **Order Service** | 8081 | Manajemen pesanan & publikasi event | PostgreSQL + Redis |
| **Notification Service** | 8082 | Konsumen notifikasi real-time | MongoDB |
| **Kitchen Service** | 8083 | Manajemen antrian dapur & status masak | MongoDB |
| **Menu Service** | 8084 | Manajemen katalog menu & aset gambar | MySQL + MinIO |

## 🛠️ Tech Stack

- **Backend:** Java 21, Spring Boot 3.4.2, Spring Cloud 2024.0.0
- **Messaging:** Apache Kafka (Confluent)
- **Caching:** Redis
- **Storage:** MinIO (Object Storage)
- **Database:** PostgreSQL, MySQL, MongoDB
- **Migration:** Flyway (Menu Service)
- **Containerization:** Docker & Docker Compose

## 🚀 Cara Menjalankan

### Prasyarat
- Docker & Docker Desktop
- Java 21 & Maven (jika ingin build manual)

### Langkah-langkah
1. **Clone Repository:**
   ```bash
   git clone https://github.com/KopiYama/manajemen-pesanan-backend.git
   cd manajemen-pesanan-backend
   ```

2. **Build Semua Service:**
   Gunakan Maven Wrapper di root (atau masuk ke tiap folder):
   ```bash
   # Contoh build manual (lewati tes untuk kecepatan)
   .\mvnw.cmd clean package -DskipTests
   ```

3. **Jalankan Infrastruktur & Service:**
   ```bash
   docker-compose up -d --build
   ```

## 📍 Endpoint API Utama (via Gateway)

Semua permintaan harus melalui API Gateway di port **8080**:

- **Order API:** `http://localhost:8080/api/orders`
- **Menu API:** `http://localhost:8080/api/menu`
- **Kitchen API:** `http://localhost:8080/api/kitchen`
- **Notification API:** `http://localhost:8080/api/notifications`

## 📊 Monitoring & Console

- **Eureka Dashboard:** `http://localhost:8761`
- **MinIO Console:** `http://localhost:9001` (User/Pass: `minioadmin` / `minioadmin123`)
- **Kafka Broker:** `localhost:29092` (Eksternal)

## 📝 Catatan Penting
- Pastikan port 8080-8084, 8761, 3306, 5432, 27017, 9000-9002, dan 6379 tersedia di mesin lokal Anda.
- Konfigurasi CORS sudah disetel untuk mengizinkan frontend dari `http://localhost:3000`.
