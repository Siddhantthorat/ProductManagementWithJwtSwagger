# ProductManagementWithJwtSwagger
### For Swagger :
  We will be adding the swagger dependency to the pom.xml file
  If we now go to swagger url - http://localhost:8080/swagger-ui/index.html we get the security exception 403.(Because we are using jwt)
  We will whitelist the swagger urls in spring security file such that it is accessible without authentication
  We will now see the swagger ui .
  However if we try to get list of Product using the url /list using the swaggerui we get 403 exception(Because we need to get authenticated and authorize).
  Next we create a class named SwaggerConfig that uses the Spring Framework's @Configuration annotation to define a bean for generating Swagger documentation. We create an OpenAPI object with information about the authentication service, including the title, description. Most importantly in this config we create a security scheme for bearer authentication, specifying the scheme name, type, and bearer format.
  Create the JWT -
Hit the url - /login to which we pass the username and password.
Provide the JWT to swagger for authorization-
Using the authorize button provide the JWT

Access list and other url -
If we now hit the url /list we get back the list of employees.
  
  
