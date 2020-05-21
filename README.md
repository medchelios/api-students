# api-etudiants
REST API STUDENT

## Used Technologies and their references
[Spring boot](https://spring.io/)
[Docker Hub](https://hub.docker.com/)
[MongoDB](https://www.mongodb.com/fr)
[Junit](https://junit.org/junit5/)

Step 1: clone project
```sh
git clone https://github.com/medchelios/api-students.git
```
Step 2: up image
```sh
./docker-compose up
```
Step3: build package and run project
```sh
./mvn package && java -jar target/api-etudiants-0.0.1-SNAPSHOT.jar
```
Step4: test all endpoint

```sh
GET ALL
For Example you can use file data.json

curl --location --request GET 'http://localhost:8081/students/'
```

```sh
SAVE

curl --location --request POST 'http://localhost:8081/students/save' \
--header 'Content-Type: application/json' \
--data-raw ' {
    "name": "Mohamed",
    "studentNumber": 115,
    "email": "moh90@test.com",
    "courseList": [
      "Docker",
      "Mongodb",
      "TDD"
    ],
    "gpa": 5.0
  }'
 ```
```sh
DELETE

curl --location --request DELETE 'http://localhost:8081/students/delete/41'
 ```
```sh
You find other endpoint on controller StudentRestController

Thanks You
 ```