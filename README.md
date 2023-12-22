# Social Media REST API

This project is a Social Media REST API built with Spring Boot.

## Table of Contents
- [Description](#description)
- [Classes](#classes)
- [Dependencies](#dependencies)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [Getting Started](#getting-started)

## Description

The Social Media REST API is designed to provide basic functionality for managing users and their posts. It includes features such as user retrieval, user creation, post retrieval, and post creation.

## Classes

### 1. `SpringSecurityConfiguration`

The `SpringSecurityConfiguration` class is responsible for configuring Spring Security. It overrides the default behavior to use HTTP Basic authentication with a popup rather than a login page.

### 2. `ErrorDetails`

The `ErrorDetails` class represents details of an error, including a timestamp, message, and additional details.

### 3. `PostRepository`

The `PostRepository` interface extends `JpaRepository` for the `Post` entity, providing CRUD operations for posts.

### 4. `UserRepository`

The `UserRepository` interface extends `JpaRepository` for the `User` entity, providing CRUD operations for users.

### 5. `CustomizedResponseEntityExceptionHandler`

The `CustomizedResponseEntityExceptionHandler` class handles exceptions globally, providing customized responses for different types of exceptions.

### 6. `Post`

The `Post` class represents a user post, including a description and a many-to-one relationship with the `User` class.

### 7. `PostNotFoundException`

The `PostNotFoundException` class is a custom exception thrown when a post is not found.

### 8. `User`

The `User` class represents a social media user, including details such as name, birth date, and a one-to-many relationship with posts.

### 9. `UserJpaResource`

The `UserJpaResource` class provides RESTful endpoints for user and post operations using Spring Data JPA.

### 10. `UserNotFoundException`

The `UserNotFoundException` class is a custom exception thrown when a user is not found.

### 11. `UserResource`

The `UserResource` class provides RESTful endpoints for user operations without using Spring Data JPA.

## Dependencies

- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Spring Boot Starter Security
- Spring Boot Starter Validation
- MySQL Connector Java
- Spring Boot Starter Hateoas
- Spring Boot Starter Actuator
- Spring Data REST HAL Explorer
- Spring Boot DevTools
- Spring Boot Starter Test

## Configuration

### `application.properties`

```properties
logging.level.org.springframework=info

management.endpoints.web.exposure.include=*

spring.jpa.defer-datasource-initialization=true
spring.jpa.show-sql=true

spring.datasource.url=jdbc:mysql://localhost:3306/social-media-database
spring.datasource.username=social-media-user
spring.datasource.password=dummypassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

spring.security.user.name=username
spring.security.user.password=password

docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8


Feel free to adjust the Docker command and instructions as needed for your specific setup. If you have any further requests or modifications, let me know!

