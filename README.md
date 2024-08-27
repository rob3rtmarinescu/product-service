# Project title

Product-service

## Getting started

This file will help you out getting familiar with the product-service. 

### Prerequisites

Java version 17, Maven and an IDE. 

### APIS exposed

POST /api/v1/products/admin/insert

    user -> server : Request POST /api/v1/products/admin/insert
    server -> user: Return the name and id of the added product

GET /api/v1/products/find

    user -> server : Request GET /api/v1/products/find
    server -> user: Return the product found or exception if the product was not found

PUT /api/v1/products/admin/price/change

    user -> server : Request PUT /api/v1/products/admin/price/change
    server -> user: 

### Versioning

We use Github for versioning. 

### Built with

Spring Boot, Spring Framework, Spring Data JPA, Spring security

### Authors

Robert Marinescu