# Banking Solution

## Overview
Banking Solution is a Spring Boot application that provides basic banking functionalities such as deposit, withdrawal, and transfer of funds between accounts. The application also includes a JaCoCo code coverage report accessible via an endpoint.

## Features
- Deposit funds into an account
- Withdraw funds from an account
- Transfer funds between accounts
- JaCoCo code coverage report

## Technologies Used
- Java
- Spring Boot
- Gradle
- JaCoCo
- Swagger
- JUnit 5
- Mockito

## Prerequisites
- Java 21
- Gradle
- PostgreSQL

## Setup

Clone the repository
```sh
git clone https://github.com/DanylkoMykola/banking-solution.git
```
```sh
cd banking-solution
```
### Build using Docker
Build image
```
docker build -t banking-solution .
```
Run docker-compose from the root directory. It will start the PostgreSQL database and the Spring Boot application. 
```
docker-compose up
```
### Manual Build
Configure the database
Update the **application.properties** file with your PostgreSQL database configuration.
Build the project
```
./gradlew clean build
```
Running Tests
To run the tests and generate the JaCoCo report:
```
./gradlew clean test jacocoTestReport
```


## Accessing the JaCoCo Report
The JaCoCo report can be accessed at the /jacoco endpoint after running the application.
URL: http://localhost:8080/test/html/index.html

## API Endpoints
### OpenAPI Documentation
The OpenAPI documentation can be accessed at the http://localhost:8080/swagger-ui/index.html endpoint after running the application.

### Account Endpoints

Create Account
```
URL: /accounts
Method: POST
Request Body:
{
  "id": 0,
  "accountNumber": 123456789012,
  "ownerName": "string",
  "balance": 0
}
```

Get Account By Account Number
```
URL: /accounts/{accountNumber}
Method: GET
```
Get All Accounts
```
URL: /accounts
Method: GET
```

### Transactions Endpoints
Deposit
```
URL: /transactions/deposit
Method: POST
Request Body:
{
  "accountNumber": 123456789012,
  "amount": 500
}
```

Withdraw
```
URL: /transactions/withdraw
Method: POST
Request Body:
{
  "accountNumber": 123456789012,
  "amount": 500
}
```
Transfer
```
URL: /transactions/transfer
Method: POST
Request Body:
{
  "accountNumber": 123456789012,
  "transferAccountNumber": 987654321098,
  "amount": 500
}
```
