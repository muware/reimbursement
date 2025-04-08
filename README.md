# Reimbursement Project

## How to Run

- Spring Boot dev server should run at 8080.
- React app should run at 3030.

### Backend
Built using Java 22.0.2 (Amazon's version) and Maven 3.9.9. 

Make sure Java 22 and Maven is installed first.

```
# Build
./mvnw clean install

# Run
./mvnw spring-boot:run
```
It should start listening at port 8080 on localhost.

### Database Console

Once the backend starts successfully, H2 console will be available at http://localhost:8080/h2-console. Just click on Connect without entering a password. This is where you can see the database records as they get populated after each form submission.

### Frontend
Built using Node v23.7.0 and NPM 11.1.0.

```
# Build
npm install

# Run
npm start
```

## Repository Links
- Backend: https://github.com/muware/reimbursement
- Frontend: https://github.com/muware/reimbursement-ui

There is no commit history, because I immediately started writing code and only initialized the git repo after I finished coding. I've been working on it since Sunday. You can see it by checking the timestamp in the log files under `logs` directory.

## Time Estimate
My estimate was 8-12 hours total for both the backend and the frontend. I believe I spent ~15 hours on it. Spring is an extensive framework and Spring Boot is an “opinionated view of the Spring platform”. A good chunk of my time was spent on tying things together the way Spring Boot understands, so that it would function as expected. I tried to use the framework’s features as much as I could to avoid writing unnecessary code. Used more time on the backend than the frontend, which also has been my professional experience so far. I didn’t get to write code for the following because of time constraints.
- Users/login
- Tests (frontend/backend)
- Security

## Tech Stack
### Backend
I used Spring Boot to build the backend since my professional web development experience in the last few years has been with Java. I haven’t had much experience with .NET-based web application development (I have C# experience in applied and research-based projects), but Java/Spring Boot concepts translate well into C#/.NET. I’m confident I can train myself and get up to speed with C#/.NET quickly.

### Frontend
I used React.js to build the frontend, because it is the library I worked with for frontend development at my last job. I also have some experience with React Native. I believe React experience would also translate well to Angular.

## Comments
### Resources
I didn’t use code generation tools. I used the following resources mostly.
- Spring Initializr to initialize a new Spring Boot project.
- A personal Spring Boot toy project which I was working on a while ago to have a better technical understanding of the framework.
- Baeldung.com, which has a lot of guides for Java and Spring development.
- React-Bootstrap documentation.
- Google and Stackoverflow to find answers to specific questions.
- I asked ChatGPT a few questions about the React frontend issues I encountered like an unexpected z-index stacking problem.

### Assumptions
- It’s ok to not worry about user accounts for this exercise.
- It's ok to store files in the database as blobs instead of the file system because of time constraints.
- Constraints used in form validation on both the backend and frontend are my assumptions.

### Database Setup
- I used Spring’s ORM feature, so that I wouldn’t need to write any SQL queries.
- Backend uses the embedded H2 database, which doesn’t persist data. When the application ends, the data is gone.


### Review Suggestion
I would suggest going through the backend in the following order:
1. **ReimbursementController** – post() method is the endpoint for submissions.
2. **PurchaseForm** – post() method’s only parameter is of type PurchaseForm. This class defines the data expected and handles validation.
3. **FileType** – In PurchaseForm, @FileType annotation is used for validating the type of receipt file uploaded. This is a custom annotation.
4. **MultipartFileTypeValidator** – This file works together with FileType. This is where the file validation logic is.
5. **PurchaseConverter** – This file converts a PurchaseForm object (submitted/validated form data) into an entity that can be stored in the database. Conversion happening in the post() method of ReimbursementController is handled by this file.
6. **PurchaseEntity** – Defines what a purchase entity looks like when it gets stored in the database.
7. **PurchaseRepository** – ORM configuration for purchase table in the database.
8. **JpaConfigration** – Configures an embedded H2 database instance.

Frontend should be easy to follow. App.js is the main file that builds the form. Other files are smaller pieces of the form.
