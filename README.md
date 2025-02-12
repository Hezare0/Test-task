
# Effective Mobile Test Task

Тестовое задание для компании Effective Mobile.

Этот проект представляет собой Spring Boot приложение, разработанное в рамках тестового задания. Приложение предоставляет REST API для системы управления задачами, а также включает аутентификацию и авторизацию с использованием JWT (JSON Web Token).




## Технологии
- Java 17
- Spring Boot 3.x
- Spring Security
- JWT (JSON Web Token)
- Liquibase (для миграций базы данных)
- PostgreSQL (база данных)
- Maven (сборка проекта)
- JUnit 5 (тестирование)
- Swagger (документация API)
## Настройка и запуск
### Требования 

- Установленная Java 17 или выше.
- Установленная PostgreSQL.
- Установленный Maven.

### Установка зависимостей

1. Клонируйте репозиторий:

```bash
  git clone https://github.com/Hezare0/Test-Task.git
  cd Test-Task
```
2. Установите зависимости с помощью Maven:
```bash
  mvn clean instal
```
### Настройка базы данных

1. Создайте базу данных в PostgreSQL:
```sql
  CREATE DATABASE test_task;
```

2. Настройте подключение к базе данных в файле application.properties:

```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/test_task
  spring.datasource.username=your_username
  spring.datasource.password=your_password
  spring.jpa.hibernate.ddl-auto=validate
```

### Запуск приложения

1. Запустите приложение:
```bash
  mvn spring-boot:run
```
2. Приложение будет доступно по адресу: http://localhost:8080
## Использование API

Просмотра документации API доступен с помощью Swagger по аддресу
```
http://127.0.0.1:8080/swagger-ui/index.html
```

## Docker

Приложение очень просто в установке и развертывании в контейнере Docker.

По умолчанию Docker предоставляет порт 80, поэтому при необходимости измените его в
Dockerfile. Когда все будет готово, просто используйте docker-compose в корне проекта для
создания контейнеров.

```bash
  docker-compose up
```

Подтвердите развертывание, перейдя по адресу вашего сервера
в предпочитаемом вами браузере.

```
127.0.0.1
```


## Тестирование

Для тестирования используются JUnit 5 и MockMvc. Запустите тесты с помощью команды:

```bash
  mvn test
```