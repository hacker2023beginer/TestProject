# TestProject

Backend-приложение для работы с отелями на **Java 21 и Spring Boot**, разработанное с использованием современного стека Java-технологий.

Проект демонстрирует построение REST-приложения с использованием **Spring Web, Spring Data JPA, валидации, миграций базы данных, DTO-маппинга и OpenAPI/Swagger**.

## 🚀 Tech Stack

| Technology        | Version / Usage               |
| ----------------- | ----------------------------- |
| Java              | 21                            |
| Spring Boot       | 3.4.4                         |
| Spring Web        | REST API                      |
| Spring Data JPA   | Работа с БД                   |
| Hibernate         | ORM                           |
| H2 Database       | Embedded database             |
| Liquibase         | Database migrations           |
| MapStruct         | DTO ↔ Entity mapping          |
| Lombok            | Boilerplate reduction         |
| Spring Validation | Input validation              |
| SpringDoc OpenAPI | API documentation             |
| Maven             | Build & dependency management |

## 📋 Project Overview

Основная задача проекта — реализовать backend-приложение на базе Spring Boot с разделением ответственности между слоями приложения и использованием стандартных подходов разработки Java REST API.

В проекте используются:

* REST API;
* Spring Data JPA для доступа к данным;
* Hibernate для ORM;
* H2 в качестве базы данных;
* Liquibase для версионирования схемы БД;
* Bean Validation для проверки входных данных;
* MapStruct для преобразования объектов;
* Lombok для уменьшения количества шаблонного кода;
* OpenAPI/Swagger для документирования API.

## 🏗 Architecture

Проект построен вокруг стандартной многослойной архитектуры Spring-приложения:

```text
Client
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository
   │
   ▼
Database
```

### Controller

Отвечает за:

* обработку HTTP-запросов;
* получение входных данных;
* валидацию запросов;
* формирование HTTP-ответов.

### Service

Содержит бизнес-логику приложения и координирует взаимодействие между контроллерами и слоем доступа к данным.

### Repository

Использует Spring Data JPA для работы с сущностями и базой данных.

### Entity / DTO

Entity представляет данные, хранящиеся в базе данных.

DTO используются для передачи данных между слоями приложения и REST API.

Для преобразования объектов используется **MapStruct**.

## 🗄 Database

Проект использует **H2 Database**.

База данных хранится в файловом режиме:

```text
./data/hotels
```

Конфигурация подключения:

```properties
spring.datasource.url=jdbc:h2:file:./data/hotels
spring.datasource.username=sa
spring.datasource.password=
```

Hibernate работает в режиме:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

Это означает, что Hibernate проверяет соответствие существующей схемы базы данных сущностям, но не занимается автоматическим изменением схемы.

## 🔄 Database Migrations

Для управления схемой базы данных используется **Liquibase**.

Основной changelog:

```text
src/main/resources/db/changelog/db.changelog-master.yaml
```

Liquibase позволяет:

* версионировать изменения структуры БД;
* последовательно применять миграции;
* сохранять историю изменений;
* поддерживать одинаковую структуру базы данных между окружениями.

## 📖 API Documentation

Для документирования REST API используется **SpringDoc OpenAPI**.

После запуска приложения Swagger UI доступен по адресу:

```text
http://localhost:8092/swagger-ui/index.html
```

OpenAPI позволяет просматривать доступные endpoints, параметры запросов, модели данных и выполнять запросы непосредственно из браузера.

## 🛠 Running the Application

### Requirements

Перед запуском необходимо установить:

* **JDK 21**
* **Git**

Maven отдельно устанавливать не требуется — проект содержит Maven Wrapper.

### Clone repository

```bash
git clone https://github.com/hacker2023beginer/TestProject.git
cd TestProject
```

### Run with Maven Wrapper

#### Linux / macOS

```bash
./mvnw spring-boot:run
```

#### Windows

```powershell
.\mvnw.cmd spring-boot:run
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:8092
```

## 🗃 H2 Console

H2 Console включена в конфигурации приложения.

URL:

```text
http://localhost:8092/h2-console
```

Параметры подключения:

```text
JDBC URL: jdbc:h2:file:./data/hotels
User Name: sa
Password:
```

## 🧪 Testing

Для тестирования используется стандартный Spring Boot Test Stack:

```xml
spring-boot-starter-test
```

Запуск тестов:

```bash
./mvnw test
```

Windows:

```powershell
.\mvnw.cmd test
```

## 📁 Project Structure

Основная структура проекта:

```text
TestProject/
├── .mvn/
│   └── wrapper/
├── data/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── vladproj/
│   │   │           └── test/
│   │   └── resources/
│   │       ├── db/
│   │       │   └── changelog/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## 🎯 Main Goals

Проект предназначен для практического применения следующих технологий и подходов:

* разработка REST API на Spring Boot;
* работа с реляционной базой данных через JPA/Hibernate;
* использование DTO;
* автоматизированный mapping объектов с MapStruct;
* валидация входных данных;
* управление схемой БД с Liquibase;
* документирование API через OpenAPI;
* написание автоматизированных тестов;
* работа с Maven и Maven Wrapper.

## 🔧 Build

Для сборки проекта:

```bash
./mvnw clean package
```

Windows:

```powershell
.\mvnw.cmd clean package
```

После успешной сборки JAR-файл будет находиться в:

```text
target/
```

Запуск собранного приложения:

```bash
java -jar target/test-0.0.1-SNAPSHOT.jar
```

## 📌 Project Status

Проект находится в стадии разработки и используется для практического применения технологий Java Backend и Spring Framework.

## 👨‍💻 Author

**Vladislav**

GitHub:

https://github.com/hacker2023beginer

---

⭐ Если проект оказался полезным или интересным, можно поставить Star репозиторию.
