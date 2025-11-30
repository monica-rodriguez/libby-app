CSE 350 libby app 

Please make sure you have the following installed:
- Java: preferably version 17 or higher 
- Git 
- Maven: 
  - there should be a mvnw wrapper: confirm with "./mvnw.cmd clean verify" in Git Bash Terminal 
  - this should allow you to skip installing maven 
    - if the maven wrapper does not work please install apache maven
        - preferably version 3.9
Check versions: 
        - java -version
        - git --version'
        - mvn -v (optional)

Notes for running the service will be provided later 
Please install postman to test endpoints locally 
Please switch to feature branches and do not push commits to main directly. When pushing feature branches alert the team for pr review. 

## RELEASE 1.0.0 
- Initial set up 
- JPA set up 
- Books class

## RELEASE 1.1.0
- Setup bean 
  - remove !test comment if choosing to run the bean during testing 
  - do not remove the related import 
- Csv loader and data setup 
  - if using intellij community edition: download a csv plug in
- Controller and Service 
  - only handles get requests at the moment
  - 
## RELEASE 1.1.1
[//] cucumber testing was excluded for this project. Tested using Mockito and Junit tests 
-In this release, Cucumber testing was added for the backend.
The main feature file is located in src/test/resources/features/book_search.feature, and it contains different scenarios to test the search functions. These include searching for a book by title, genre, and author’s name, as well as checking that the system shows the proper error message when no search parameters are provided.

The step definitions for these tests are in src/test/java/com/starlight/app/StepDefinition/steps.java, and the Cucumber test runner file is src/test/java/com/starlight/app/CucumberIT.java. The tests use the books.csv file in the resources folder as the data source.

To run the tests, open the terminal in VS Code and type ./mvnw test or mvn test. After the tests finish running, a report will be created in target/cucumber-report.html showing the results. If everything is working correctly, all the test scenarios should pass. If a scenario fails, it means something might be wrong with the endpoint or the data being returned.
 
## RELEASE 1.2.0 
- Adds unit tests and unit service implementation tests (all passing)
- Cucumber file, service tests and step definitions need fixing (files are commented out for testing purposes, please remove the comments to see the errors)

## RELEASE 2.0.0
- Adds lombok processing plugin to the pom file 
- Adding case-insensitivity for author, title, and genre search 
- Adding list capability for users to add books to a collection of current reads, finished, and tbr (to be read).

## RELEASE 2.1.0
- Adds the cross-origin link to connect to the react dev server
- Run mvn spring-boot:run for the backend to run 
- Run npm run start in the front end to get it rendering. 

## RELEASE 2.1.1
- Adds the cross-origins to the UserListController