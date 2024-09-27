# Product Management with JWT and Swagger

## Overview

This project demonstrates how to use JWT authentication with Swagger UI in a Spring Boot application for product management.

## Setup

1. **Add Swagger Dependencies**: Include the necessary Swagger dependencies in your project.

2. **Configure Security**: Update your Spring Security settings to allow access to Swagger endpoints without authentication.

3. **Swagger Configuration**: Create a configuration class to set up Swagger and define the JWT security scheme.

## JWT Authentication

1. **Obtain JWT**: Use the `/login` endpoint with your credentials to receive a JWT.

2. **Authorize in Swagger**: In Swagger UI, click the "Authorize" button and enter your JWT in the format: `Bearer <your_jwt_token>`.

## Accessing the API

- Use the `/list` endpoint to get the product list (authentication required).

## Conclusion

This setup provides secure product management using JWT and comprehensive API documentation through Swagger.
  
  
