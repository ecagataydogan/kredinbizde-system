# Kredin Bizde

Kredinbizde is a system where users can register and create applications.

## System Architecture

- **kredinbizde-service:** Receives users' registration requests, produces a notification according to the user's request, and writes these requests to the RabbitMQ queue.
- **notification-service:** Listens to the RabbitMQ queue and takes action.
- **akbank-service:** Applications received by kredinbizde-service are evaluated by akbank-service.
- **kredinbizde-discovery:** Eureka server, services register to this service.
- **kredinbizde-gw:** It is the gateway of the system opening to the outside.

![Ekran Resmi 2024-04-07 23 01 16](https://github.com/ecagataydogan/kredinbizde-system/assets/101594855/acdfccf7-574e-46aa-a23c-633bd2d8c821)


## Technologies

- Spring Boot
- Spring Data JPA
- Unit Test
- MySQL
- RabbitMQ
- Redis
- Eureka Server
- Swagger

## How to run

If you don't change anything, the gateway will work on port 8084. So you can send your requests to localhost:8084/api/v1/..

Prerequisite: You need to have maven, docker, rabbitmq and redis

### RabbitMQ
```code
docker run -d --hostname my-rabbit --name myrabbit -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=123456 -p 5672:5672 -p 15672:15672 rabbitmq:3-management
```

### Redis
```code
docker run --name my-redis-container -d -p 6379:6379 redis
```
Set port information to 6379

### Maven run for services (run seperately, change jar file name according to service)
```code
maven clean install
```
```
code java -jar target/kredinbizde-service-0.0.1-SNAPSHOT.jar
```


## API Endpoints

- POST `api/v1/users` -> create user
- GET `api/v1/users` -> get all users
- GET `api/v1/users/{userId}` -> get all users
- PUT `api/v1/users/{userId}` -> update user by id

- POST `api/v1/applications` -> create application
- GET `api/v1/applications` -> get all applications
- GET `api/v1/applications/{applicationId}` -> get application by id


## API Documentation

Used Swagger to document the API. 

After running the project, you can click the link below to see how to use the API.

[API Documentation](http://localhost:8080/swagger-ui/index.html#/)

![Ekran Resmi 2024-04-08 00 26 07](https://github.com/ecagataydogan/kredinbizde-system/assets/101594855/2a967966-2c9e-423a-b6a1-35a21a94baeb)



## API Usage 

Added postman collection for usage. You can import to postman and try.

kredinbizde-gw.postman_collection.json

### 1. Create user

**HTTP Metodu:** `POST`

**Endpoint:** `api/v1/users`

**Request:**
```json
{
    "name":"esref",
    "surname":"dogan",
    "birthDate": "2024-04-05",
    "email":"ecagataydogan@gmail.com",
    "password":"password",
    "phoneNumber":"555555555",
    "address":{
        "addressTitle":"title",
        "addressDescription":"description",
        "province":"province"
    }
}
```

**Response:**
```json
{
    "id": 1,
    "name": "esref",
    "surname": "dogan",
    "birthDate": "2024-04-05",
    "email": "ecagataydogan1@gmail.com",
    "password": "password",
    "phoneNumber": "555555555",
    "isActive": true,
    "address": {
        "id": 1,
        "addressTitle": "title",
        "addressDescription": "description",
        "province": "province"
    },
    "applications": []
}
```
**Email already exist exception:**
```json
{
    "message": "email already exists",
    "httpStatus": "NOT_FOUND"
}
```

### 2. Get all users

**HTTP Metodu:** `GET`

**Endpoint:** `api/v1/users`

**Response:**
```json
[
    {
        "id": 1,
        "name": "esref",
        "surname": "dogan",
        "birthDate": "2024-04-05",
        "email": "ecagataydogan1@gmail.com",
        "password": "password",
        "phoneNumber": "5549969627",
        "isActive": true,
        "address": {
            "id": 1,
            "addressTitle": "title",
            "addressDescription": "description",
            "province": "province"
        },
        "applications": []
    },
    {
        "id": 2,
        "name": "test",
        "surname": "test",
        "birthDate": "2024-04-05",
        "email": "test@gmail.com",
        "password": "test",
        "phoneNumber": "999999999",
        "isActive": true,
        "address": {
            "id": 2,
            "addressTitle": "title",
            "addressDescription": "description",
            "province": "province"
        },
        "applications": []
    }
]
```
### 3. Get user by id

**HTTP Metodu:** `POST`

**Endpoint:** `api/v1/users/{userId}`

**Response:**
```json
{
    "id": 1,
    "name": "esref",
    "surname": "dogan",
    "birthDate": "2024-04-05",
    "email": "ecagataydogan1@gmail.com",
    "password": "password",
    "phoneNumber": "555555555",
    "isActive": true,
    "address": {
        "id": 1,
        "addressTitle": "title",
        "addressDescription": "description",
        "province": "province"
    },
    "applications": []
}
```
**User not found exception:**
```json
{
    "message": "user not found",
    "httpStatus": "NOT_FOUND"
}
```
### 4. Update user by id

**HTTP Metodu:** `POST`

**Endpoint:** `api/v1/users/{userId}`

**Request:**
```json
{
    "name":"cagatay",
    "password": "passwordchange",
    "phoneNumber": "999999"
}
```

**Response:**
```json
{
    "id": 1,
    "name": "cagatay",
    "surname": "dogan",
    "birthDate": "2024-04-05",
    "email": "ecagataydogan@gmail.com",
    "password": "passwordchange",
    "phoneNumber": "999999",
    "isActive": true,
    "address": {
        "id": 1,
        "addressTitle": "title",
        "addressDescription": "description",
        "province": "province"
    },
    "applications": []
}
```
### 5. Create application

**HTTP Metodu:** `POST`

**Endpoint:** `api/v1/applications`

**Request:**
```json
{
    "userId":1
}
```

**Response:**
```json
{
    "id": 2,
    "userId": 1,
    "applicationStatus": "INITIAL"
}
```
### 6. Get all aplications 

**HTTP Metodu:** `GET`

**Endpoint:** `api/v1/applications`


**Response:**
```json
[
    {
        "id": 1,
        "userId": 1,
        "applicationStatus": "INITIAL"
    },
    {
        "id": 2,
        "userId": 1,
        "applicationStatus": "INITIAL"
    }
]
```
### 7. Get application by id 

**HTTP Metodu:** `GET`

**Endpoint:** `api/v1/application/{applicationId}`

**Response:**
```json
{
        "id": 1,
        "userId": 1,
        "applicationStatus": "INITIAL"
    }
```
**Application not found exception:**
```json
{
    "message": "application not found",
    "httpStatus": "NOT_FOUND"
}
```





