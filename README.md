# spring-boot-rest-api-liquibase-mysql-docker
Code Challenge with SpringBoot, REST API, MySQL, Liquibase, Docker

# Local Environment Requirements:
Maven 3.x

Docker

# In the root folder run below commands:

mvn install

docker-compose build --no-cache

docker compose up --force-recreate

app will be running on: http://localhost:8080/

Swagger UI will be running on: http://localhost:8080/swagger-ui/index.html#/

You can find 

# Course API CRUD endpoints: 
Postman collections on root folder: courses.postman_collection.json

Base URL: http://localhost:8080/v1/courses
<br>
<br>
<br>
List all courses: GET /v1/courses
<br>
<br>
<br>
Get a course by ID: GET /v1/courses/{id}
<br>
<br>
<br>
Create a new course: POST /v1/courses

JSON body:
{
"name": "COURSE 6"
}
<br>
<br>
<br>
Update an existing course: PUT /v1/courses/{id}

JSON body:
{
"id": 6
"name": "COURSE 6 updated"
}
<br>
<br>
<br>
Delete an existing course: DELETE /v1/courses/{id}


# Student API CRUD endpoints:
Postman collections on root folder: students.postman_collection.json

Base URL: http://localhost:8080/v1/students
<br>
<br>
<br>
List all students: GET /v1/students
<br>
<br>
<br>
Get a student by ID: GET /v1/students/{id}
<br>
<br>
<br>
Create a new student: POST /v1/students

JSON body:
{
"name": "STUDENT 6"
}
<br>
<br>
<br>
Update an existing student: PUT /v1/students/{id}

JSON body:
{
"id": 6
"name": "STUDENT 6 updated"
}
<br>
<br>
<br>
Delete an existing student: DELETE /v1/students/{id}


# Registrations and Filters operations:
Create API for students to register to courses:
/v1/students/{studentId}/register/{courseId}
<br>
<br>

Create abilities for user to view all relationships between students and courses

GET /v1/students

GET /v1/courses
<br>
<br>
<br>
Filter all students with a specific course

GET /v1/courses/{id}
<br>
<br>
<br>
Filter all courses for a specific student: 

GET /v1/students/{id}
<br>
<br>
<br>
Filter all courses without any students

GET /v1/courses/no-registrations
<br>
<br>
<br>
Filter all students without any courses

GET /v1/students/no-registrations