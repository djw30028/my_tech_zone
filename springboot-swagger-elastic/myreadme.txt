lsof -i tcp:8088

http://localhost:8088

{
  "firstName" : "Brian",
  "lastName" : "Fowler"
}

curl -X GET --header 'Accept: application/json' 'http://localhost:8088/bradways/greeting?name=brian'


====================================================
== Add AngularJS 2: https://angular.io/docs/js/latest/quickstart.html
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
  Creaste new 
  app/app.module.ts 
  
  
Step 5: Create a component and add it to your application
  /app.component.ts

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
== Integrate augularJS2, Spring boot maven
== http://stackoverflow.com/questions/35704973/how-to-configure-angular2-application-using-typescript-with-maven

Step 1: create controller 
     @RequestMapping("/angular2maven")
	 public String angular2maven(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
	     model.addAttribute("name", name);
	     return "angular2_maven/angular2maven";
	 }
  test: http://localhost:8088/angular2maven

Step 2: modify pom.xml
  copy or create new: src/main/resources/js/systemjs.config.js
  
Step 3: 
 Copy package.json, tsconfig.json to src/main/resources/resources
 mvn clean package --> generate node_modules
 
Step 4: 
 copy or create:
 1. app.component.ts
  2. app/app.module.ts 
  3. app/main.ts
  


