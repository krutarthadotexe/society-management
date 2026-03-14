🏢 Society Management System

A Society Management System built using Java and Spring Boot to digitize and simplify the management of residential societies.
The system allows administrators to manage residents, post notices, track maintenance payments, and handle complaints through a centralized platform.

This project was developed as part of a Computer Engineering academic project to demonstrate backend development using Spring Boot and RESTful APIs.
📌 Overview

Residential societies often manage records such as resident information, complaints, and notices manually, which can be inefficient and error-prone.

This system provides a backend application that helps automate these tasks and provides a structured way to manage society operations.

✨ Features

👤 Resident Management
	•	Add new residents
	•	Update resident details
	•	Remove residents
	•	Maintain flat ownership records

📢 Notice Management
	•	Admin can publish society notices
	•	Residents can view important announcements

🛠 Complaint Management
	•	Residents can register complaints
	•	Admin can track and resolve complaints

💰 Maintenance Tracking
	•	Record maintenance payments
	•	Track pending dues
  . Integrated RazorpayAPI for payments

🔐 Authentication System
	•	Secure login functionality
	•	Role-based access (Admin / Resident)

🏗 System Architecture
Client (Postman / Frontend)
        │
        ▼
REST Controllers
        │
        ▼
Service Layer (Business Logic)
        │
        ▼
Repository Layer (Spring Data JPA)
        │
        ▼
Database (MySQL)

🛠 Tech Stack
Technology.     Description
Java.           core programming language
Spring Boot.    backend application framework
Spring Data JPA ORM for database interaction
Hibernate.      Object Relational Mapping
MySQL.          Relational database
Maven.          Build and dependency management
REST APIs.      Communication between services
Postman.        API testing

📂 Project Structure
society-management-system
│
├── src/main/java
│   ├── controller        # REST API controllers
│   ├── service           # Business logic
│   ├── repository        # Database repositories
│   ├── entity            # Database models
│   ├── dto               # Data transfer objects
│   └── SocietyManagementApplication.java
│
├── src/main/resources
│   └── application.properties
│
└── pom.xml               # Maven dependencies

⚙️ Installation & Setup

1️⃣ Clone the Repository
git clone https://github.com/krutarthadotexe/society-management.git
cd society-management

2️⃣ Configure Database

Update the application.properties file:
spring.datasource.url=jdbc:mysql://localhost:3306/society_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

3️⃣ Build the Project:
   mvn clean install

4️⃣ Run the Application:
  mvn spring-boot:run
The application will run on:
http://localhost:8080


🧪 Testing
API endpoints can be tested using:
	•	Postman
	•	cURL
	•	Swagger (if enabled)

📈 Future Improvements
	•	Web frontend (React / Angular)
	•	Mobile app integration
	•	Visitor management system
	•	Push notifications
	•	Parking management

👨‍💻 Author:

Krutartha Thakur
Computer Engineering Student




