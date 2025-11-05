# Hospital Management System

## Overview
This repository contains a Swing-based Hospital Management System scaffolded for enterprise-ready deployments. The project targets **Java 24**, adheres to an MVC + DAO architecture, and integrates with **MySQL 8** through HikariCP. Modern UI defaults and i18n (English/Arabic) are provided out of the box to streamline further feature development.

## Features
- Modular Maven build targeting Java 24
- Configurable MySQL connection pooling via HikariCP
- ResourceBundle-powered i18n with English and Arabic bundles
- Swing UI using `GroupLayout` and `.form` files compatible with NetBeans/IntelliJ GUI builders
- DAO layer with reusable JDBC helpers
- Appointment service with scheduling validation and conflict detection
- Reporting utilities generating PDF invoices (OpenPDF) and CSV exports (Apache Commons CSV)
- BCrypt password utilities for secure credential storage
- SQL schema & seed scripts for MySQL provisioning
- JUnit 5 tests with Mockito-based isolation
- GitHub Actions workflow for CI checks

## Project Structure
```
hospital-ms/
  config/app.properties
  db/schema.sql
  db/seed.sql
  pom.xml
  src/main/java/com/acme/hms/...
  src/main/resources/...
  src/test/java/com/acme/hms/...
```

## Prerequisites
- JDK 24
- Maven 3.9+
- MySQL 8

## Setup
1. Create a MySQL database and user:
   ```sql
   CREATE DATABASE hospital_ms CHARACTER SET utf8mb4;
   CREATE USER 'hms_user'@'%' IDENTIFIED BY 'hms_pass';
   GRANT ALL PRIVILEGES ON hospital_ms.* TO 'hms_user'@'%';
   FLUSH PRIVILEGES;
   ```
2. Update `config/app.properties` with your database host, name, user, and password.
3. Execute schema and seed scripts:
   ```bash
   mysql -u hms_user -p hospital_ms < db/schema.sql
   mysql -u hms_user -p hospital_ms < db/seed.sql
   ```

## Building & Running
```bash
mvn clean package
java -cp target/hospital-ms-1.0.0.jar com.acme.hms.Main
```
The UI launches with the primary dashboard and navigation sidebar. Locale and theming can be adjusted by editing `config/app.properties` (`app.locale=en` or `app.locale=ar`).

## Testing
```bash
mvn test
```

## Continuous Integration
GitHub Actions workflow (`.github/workflows/ci.yml`) runs unit tests and static analysis on every push.

## License
Distributed under the MIT License. See `LICENSE` for details.
