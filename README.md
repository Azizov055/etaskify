# Run commands below to deploy application
- mvn clean compile package
- docker build -t etaskify .
- docker run -d -p 8081:5000 etaskify

# To watch APIs you can via swagger
http://localhost:8081/swagger-ui.html

# Description of APIs
## Auth
- POST /api/auth/register - registration admin user
- POST /api/auth/login - sign in (admin and other users)

## Users
- GET /api/users - getting users of organization (ADMIN access)
- POST /api/users - create user of organization (ADMIN access)

## Tasks
- POST /api/tasks - create a task and assign to users (ADMIN access)
- GET /api/tasks - getting all tasks of organization (ADMIN access)
- GET /api/tasks/my - getting my tasks
