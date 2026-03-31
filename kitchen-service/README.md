# Kitchen Service

## Overview
Kitchen Service is responsible for managing the order preparation workflow in the restaurant management system. It consumes order events from Kafka and tracks their progress through the kitchen pipeline.

## Tech Stack
- **Java 21**
- **Spring Boot 3.4.2**
- **MongoDB** (Database)
- **Apache Kafka** (Messaging)

## Kitchen Workflow Pipeline
1. **QUEUED**: Order received from Kafka, waiting to be cooked.
2. **IN_PROGRESS**: Order is currently being prepared.
3. **READY_TO_SERVE**: Order is cooked and ready for the customer.
4. **COMPLETED**: Order has been served and archived.

## API Documentation

### Queue Management
- `GET /api/kitchen/queue`: List all QUEUED orders.
- `PUT /api/kitchen/queue/{orderId}/process`: Move order from QUEUED to IN_PROGRESS.

### In-Progress Management
- `GET /api/kitchen/in-progress`: List all IN_PROGRESS orders.
- `PUT /api/kitchen/in-progress/{orderId}/ready`: Move order from IN_PROGRESS to READY_TO_SERVE.

### Ready to Serve Management
- `GET /api/kitchen/ready`: List all READY_TO_SERVE orders.
- `PUT /api/kitchen/ready/{orderId}/complete`: Move order from READY_TO_SERVE to COMPLETED.

### General
- `GET /api/kitchen/orders`: List all active orders (excluding COMPLETED).
- `GET /api/kitchen/orders/{orderId}`: Get details of a specific order.

## Setup Instructions
1. Ensure MongoDB is running at `localhost:27017`.
2. Ensure Kafka is running at `localhost:9092`.
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The service will be available at `http://localhost:8083`.
