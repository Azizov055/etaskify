# Run commands below to deploy application
- mvn clean compile package
- docker build -t etaskify .
- docker run -d -p 8081:5000 etaskify

# To watch APIs you can via swagger
http://localhost:8081/swagger-ui.html

# Description of APIs
