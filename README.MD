![Alt text](images/AppSmartLogo.png)

# Customer product service API

![Build](https://github.com/maqfromspace/appsmart-rest-service/actions/workflows/build.yml/badge.svg)

[![codecov](https://codecov.io/gh/maqfromspace/appsmart-rest-service/branch/dev/graph/badge.svg?token=2SVL4OQM63)](https://codecov.io/gh/maqfromspace/appsmart-rest-service)

## Содержание:
- [Краткое описание](#Introduction)
- [Используемые технологии](#Stack)
- [Требования к установке на локальный компьютер](#Requirements)
- [Инструкция по установке на локальный компьютер](#Instruction)
- [Тестирование](#Test)
- [Heroku](#Heroku)
- [Swagger](#Swagger)
- [Авторизация](#Auth)
- [Замечание по авторизации](#Auth)
- [Базы данных](#Db)
- [CI/CD](#CI/CD)





# Введение <a name="Introduction"></a>

Данный REST сервис реализует обработку заказчиков и продуктов. 

С его помощью можно выполнять следующие действия:

- Создать заказчика
- Удалить заказчика
- Изменить заказчика  
- Добавить продукт заказчику
- Удалить продукт
- Изменить продукт


# Используемые технологии <a name="Stack"></a>


- Java 8
- Spring Boot
- Spring HATEOAS
- Spring Data  
- Spring Security 
- Postgres
- Maven
- JUnit
- Docker
- Swagger
- Heroku

# Требования к установке на локальный компьютер<a name="Requirements"></a>
- Git
- Maven
- Docker (Опционально)
- Postgres (Используется соединение с следующими настройками: 
  
        url = jdbc:postgresql://localhost:5432/appsmart
        username = appsmartuser
        password = *****

# Инструкция по установке на локальный компьютер<a name="Instruction"></a>

## Maven

1) Склонировать репозиторий https://github.com/maqfromspace/appsmart-rest-service.git
2) Перейти в директорию с проектом
3) Выполнить mvn clean install
4) Запустить командой java -jar  appsmart-rest-service.jar, который будет находится в директории проекта target

## Docker

1) Склонировать репозиторий https://github.com/maqfromspace/appsmart-rest-service.git
2) Перейти в директорию с проектом
3) Выполнить mvn clean install
4) Выполнить команду 
   docker build -t <image_tag> . && docker run --name appsmart-rest-service-image -p 8080:8080

# Тестирование <a name="Test"></a>

Для проверки кода используюся sonarqube и codecov

# Heroku <a name="Heroku"></a>

Данное приложение выкатывается на heroku.

Поскольку сервис Heroku переводит неиспользуемые приложения в спящий режим, 
иногда необходимо подождать для того, чтобы приложение проснулось и начало свою работу.

# Swagger <a name="Swagger"></a>

Ознакомиться с доступными методами сервиса можно по адресу:

https://appsmart-rest-service.herokuapp.com/swagger-ui.html


# Авторизация <a name="Auth"></a>

Данный сервис имеет защиту мутабельных методов с помощью spring security.

Они могут быть выполнены только пользователем с ролью ADMIN_ROLE.

Данная проверка выполняется с помощью проверки токена следующего типа:

        Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOX1JPTEUiXSwiaWF0IjoxNjIwMzkzMTEzLCJleHAiOjE2MjAzOTY3MTN9.0cNz7mYKsNX2ktIn80KeBoiwXOZ79vBSlcSaqDSLZIQ

Данный токен можно получить путем отправки POST запроса на :

    https://appsmart-rest-service.herokuapp.com/api/v1/auth/login

либо локальный инстанс приложения:

    https://localhost:8080/api/v1/auth/login

c следующим телом запроса:

        {
            "username": "admin",
            "password": "adminpassword"
        }



При успешной авторизации будет получен следующий ответ, где token необходимый для авторизации токен:

    {
        "username": "admin",
        "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIkFETUlOX1JPTEUiXSwiaWF0IjoxNjIwMzk1OTk3LCJleHAiOjE2MjAzOTk1OTd9.aJ0Eh-8iUyV_QBiJP61qv-BoVYhL8sE_PaVz5mu4UHk"
    }


# Замечание по авторизации <a name="Auth!"></a>
Для демонстрации работы авторизации, при запуске приложения в базу данных user добавляется пользователь admin с закодированным паролем 'adminpassword',
в таблицу role добавляется роль ADMIN_ROLE и в таблицу для маппинга добавляется связка user_id <-> role_id.

На текущий момент сервис не поддерживает создание новых ролей или пользователей, но при необходимости их можно добавить в базу вручную.


# Базы данных <a name="Db"></a>
Отношение и схемы таблиц customers и products
![Alt text](images/db1.png)

Отношение и схемы таблиц users,roles и user_roles

![Alt text](images/db2.png)

# CI/CD <a name="CI/CD"></a>
Процесс сборки, тестирования и выкатки можно посмотреть тут:

https://github.com/maqfromspace/appsmart-rest-service/actions



