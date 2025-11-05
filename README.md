# Hospital Management System

Enterprise-ready Hospital Management System desktop application built with **Java 24** and **Swing** using an **MVC + DAO** architecture.

## Features

- Modular architecture with configuration, DAO, service, and UI layers
- Internationalization (English/Arabic) with runtime switch support
- Role-based navigation scaffold (Admin, Doctor, Nurse, Reception, Pharmacy, Lab, Billing)
- MySQL schema and seed scripts with realistic starter data
- JDBC connectivity using HikariCP connection pooling
- PDF invoice generation and CSV export for pharmacy inventory
- Unit tests (JUnit 5 + Mockito) and GitHub Actions CI pipeline
- Externalized configuration via `config/app.properties`

## Requirements

- JDK 24
- Maven 3.9+
- MySQL 8+

## Setup

1. Clone the repository and install dependencies:

   ```bash
   mvn clean install
   ```

2. Create a MySQL database, e.g. `hms`, and update `config/app.properties` with credentials:

   ```properties
   db.url=jdbc:mysql://localhost:3306/hms?useSSL=false&serverTimezone=UTC
   db.user=hms_user
   db.password=strong_password
   app.locale=en
   ```

3. Run database migrations:

   ```bash
   mysql -u hms_user -p hms < db/schema.sql
   mysql -u hms_user -p hms < db/seed.sql
   ```

4. Launch the desktop application:

   ```bash
   mvn exec:java -Dexec.mainClass="com.acme.hms.Main"
   ```

## IDE Integration

- The project includes Swing `.form` files compatible with NetBeans and IntelliJ GUI Builder.
- No JPMS modules are used to ensure IDE friendliness.
- UI uses `GroupLayout` to maintain GUI builder compatibility.

## Testing

```bash
mvn test
```

## Continuous Integration

GitHub Actions workflow at `.github/workflows/ci.yml` runs `mvn -B verify` and reports results on every push.

## Sample Credentials

Seed data creates the following accounts (password hashes use `admin123`):

| Username   | Role     |
|------------|----------|
| admin      | ADMIN    |
| dr_smith   | DOCTOR   |
| nurse_jane | NURSE    |
| reception  | RECEPTION|

## License

[MIT](LICENSE)
