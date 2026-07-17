# ClaimSphere
Enterprise-grade Insurance Claims Management System built with Spring Boot, Spring Security, JWT &amp; OAuth2.

ClaimSphere is an enterprise-grade Insurance Claims Management System built with **Java 21** and **Spring Boot 3**. The project is designed to demonstrate production-ready backend development practices including authentication, authorization, REST APIs, Spring Security, JWT, OAuth2, and clean architecture.

> **Status:** Active Development 🚧

## Tech Stack

* Java 21
* Spring Boot 3
* Spring Security 6
* Spring Data JPA
* Microsoft SQL Server
* JWT Authentication
* OAuth2 Login (Google)
* Maven
* Lombok
* Git & GitHub
* Bruno / Postman

---

## Features Implemented

### Authentication

* User Registration
* User Login
* BCrypt Password Encryption
* JWT Access Token Generation
* JWT Authentication Filter
* Custom UserDetailsService
* Custom User Principal

### OAuth2

* Google OAuth2 Login
* Automatic User Provisioning
* Existing User Linking
* OAuth2 Success Handler
* JWT Generation after Google Login

### Security

* Spring Security Configuration
* Stateless Authentication
* Password Encryption
* Public & Protected Endpoints
* Method Security Ready
* Cookie-based JWT support (in progress)

### REST APIs

* Authentication APIs
* Secure Endpoints
* Validation
* Specification
* DTOs for Request & Response
* Error Handling

### Architecture

* Package-by-Feature
* Constructor Injection
* Service Layer
* Repository Layer
* DTO-Based Design
* Clean Separation of Concerns

---

## Project Structure

```text
src/main/java/com/claimsphere
│
├── auth
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
│
├── security
│   ├── filter
│   ├── jwt
│   ├── oauth
│   ├── config
│   └── AppUserPrincipal
│
└── common
```

---

## Authentication Flow

### Local Login

```text
Client
   │
   ▼
Login API
   │
   ▼
AuthenticationManager
   │
   ▼
JWT Generated
   │
   ▼
Client Stores Token
   │
   ▼
Authorization: Bearer <JWT>
```

### Google OAuth2 Login

```text
User
   │
   ▼
Google OAuth2
   │
   ▼
Spring Security
   │
   ▼
OAuth2 Success Handler
   │
   ▼
JWT Generated
   │
   ▼
Browser Redirect
```

---

## Current Progress

* ✅ Spring Boot Setup
* ✅ SQL Server Integration
* ✅ Spring Data JPA
* ✅ User Registration
* ✅ Login API
* ✅ JWT Authentication
* ✅ Spring Security
* ✅ Google OAuth2 Login
* ✅ JWT + OAuth2 Integration
* ✅ Redis Caching
* 🚧 Cookie-based Authentication
* ⏳ Claims Module
* ⏳ Policy Module
* ⏳ Customer Module
* ⏳ Document Management
* ⏳ Admin Dashboard

---

## Future Roadmap

* Claims Management
* Policy Management
* Customer Management
* File Upload
* Email Notifications
* Audit Logging
* Kafka Integration
* Docker
* Kubernetes
* Azure Deployment
* CI/CD with GitHub Actions
* Spring AI Integration

---

## Getting Started

### Clone Repository

```bash
git clone https://github.com/srinivasreddyannapureddy/ClaimSphere.git
```

### Build

```bash
mvn clean install
```

### Run

```bash
mvn spring-boot:run
```

---

## Author

**Srinivas Annapureddy**

Senior Software Developer

Java | Spring Boot | REST APIs | SQL Server | Spring Security | OAuth2 | JWT

---

## License

This project is for learning, portfolio, and demonstration purposes.
