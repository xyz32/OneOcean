# OneOcean vessels tracker

## Prerequisite
The project requires IntelliJ, and Java 11 +.

Maven can optionally be installed or one can use the IntelliJ builtin implementation.

For the API docs to work, one needs to run `mvn clean install` at least once. This command will generate the API docs html. 

## How to use

Open the project in IntelliJ, navigate to the Main.java file and run the `static main()` method.
This will start the REST backend server.

Open a browser and navigate to:

- http://localhost:8080/index.html
  - for the main app
- http://localhost:8080/docs
  - to see the API docs. Requires the `mvn clean install` command to be executed at least once.