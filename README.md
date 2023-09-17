# post-me :left_right_arrow:

Some kind of simple social media with authorisation and posting. You can follow or make friends with somebody you like.

## Features

* Java
* Spring boot
* Maven
* Docker
* Coverage by Postman tests, Unit tests, Integration tests
* Custom exceptions
* PostgresSQL, Hibernate and JPA
* Spring security with JWT hashing of passwords
* Redis
* Swagger documentation (https://app.swaggerhub.com/apis/ANNA1637/post-me/1.0.0)

## How to install

1. Download and install Java 11
2. Download and install Maven
3. Download and install Docker
4. Clone this repo

## How to use

1. Package maven project
2. Open Docker and run docker-compose.yml
3. Access endpoints through http://localhost:8080

## How to test by Postman

1. Download tests from /postman
2. Create user in Postman ( /auth/Registration )
3. Get token from Authentication
4. Save token into collection variable "firstToken"
5. Repeat with the second user
6. Run the other folders

## Plans for future

:white_check_mark: Redis    
:black_square_button: >70% Coverage by tests    
:black_square_button: Automatic Postman testing
:black_square_button: Upgrade of images  
