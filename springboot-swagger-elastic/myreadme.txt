lsof -i tcp:8088

http://localhost:8088

{
  "firstName" : "Brian",
  "lastName" : "Fowler"
}

curl -X GET --header 'Accept: application/json' 'http://localhost:8088/bradways/greeting?name=brian'


====================================================
== Add AngularJS 2
10/25/2016

AngularJs2Controller
http://localhost:8088/angular2

Step 1: Create src/main/webapp/frontend
Step 2: Create package.json
  package.json identifies npm package dependencies for the project.
  tsconfig.json defines how the TypeScript compiler generates JavaScript from the project's files.
  systemjs.config.js provides information to a module loader about where to find application modules, 
Step 3: Install packages
  npm install
  
Step 4: Create your application
  Create an app sub folder
  
Step 5: Create a component and add it to your application

Step 6: Start up your application
   app/main.ts

Step 7: Define the web page that hosts the application

Step 8: Build and run the application webapp/frontend
   npm start  --> it is not working any more after change in the tricks below:

**** Tricks:   
In systemjs.config.js:
 FROM:
  'npm:': 'node_modules/'
  'main: './app/main.js',
 TO: 
  'npm:': 'frontend/node_modules/'
   main: '../frontend/app/main.js',

angular2.jsp include index.html

mvn clean package
java -jar target/springboot-swagger-elastic-1.4.0.RELEASE.jar
http://localhost:8088/angular2

=============================================
==


