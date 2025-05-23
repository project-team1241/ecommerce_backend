# 🌟 E-Commerce Backend  

[![Maven Build](https://github.com/project-team1241/ecommerce_backend/actions/workflows/maven-build.yml/badge.svg?event=push)](https://github.com/project-team1241/ecommerce_backend/actions/workflows/maven-build.yml)

![Project Logo](https://via.placeholder.com/728x90.png?text=E-Commerce+Backend)  

An advanced **Spring Boot** backend application for e-commerce platforms. This project implements secure authentication and authorization using **JWT**, **OAuth**, and **Spring Security**, and is ready for scalable deployments using Docker.  

---

## 🚀 Features  

- 🛡️ **Secure APIs**: Authentication and authorization with **JWT** & **OAuth**.  
- 👥 **Role-Based Access Control**: Restrict access to specific APIs.  
- 📦 **Product Management**: CRUD operations for products.  
- 📜 **Order Management**: Handle orders seamlessly.  
- 🐳 **Dockerized Deployment**: Easy containerization using **Docker Compose**.  
- ⚡ **High Performance**: Optimized for scalability and extensibility.  

---

## 🛠️ Tech Stack  

| **Technology**     | **Version**   | **Purpose**                     |  
|---------------------|---------------|----------------------------------|  
| **Java**           | 21+           | Core development language       |  
| **Spring Boot**    | Latest        | Backend framework               |  
| **Spring Security**| Latest        | Authentication & Authorization  |  
| **JWT & OAuth**    | Latest        | Secure token-based auth         |  
| **Maven**          | Latest        | Build and dependency management |  
| **Docker**         | Latest        | Containerization                |  
| **Docker Compose** | Latest        | Multi-container orchestration   |  

---

## 📖 Getting Started  

### Prerequisites  

Ensure the following are installed:  
- **Java** (JDK 21 or higher)  
- **Maven**  
- **Docker Desktop** or another Docker client  [0ptional]

### Setup  

1. **Clone the Repository**  
   ```bash
   git clone https://github.com/your-username/ecommerce-backend.git
   cd ecommerce-backend
   ```
** Note : Try to use our dev/test db in the .evn-dev

   ```bash
   mvn clean install -DskipTests
   mvn clean package -DskipTests
   ```

   ```bash
   mvn spring-boot:run
   ```
---

### Docker

   ```bash
   docker-compose up --build
   docker-compose down
   
   docker tag ecommerce-backend <your-registry>/<project-name>:latest
   docker push <your-registry>/<project-name>:latest
   ```
---

