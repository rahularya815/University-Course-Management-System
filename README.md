# 🎓 University Course Management System API

A robust RESTful API built with Spring Boot to manage students, professors, courses, and enrollments in a university ecosystem. This project features a secure authentication system using JSON Web Tokens (JWT) with role-based access control (RBAC) to protect endpoints.

---

## ✨ Features

-   **User Authentication**: Secure user registration and sign-in generating a JWT.
-   **Role-Based Access Control (RBAC)**: Distinct permissions for `STUDENT` and `PROFESSOR` roles.
-   **Professor Management**: Endpoints for professors to manage their profiles and the courses they teach.
-   **Student Management**: Endpoints for students to manage their profiles and course enrollments.
-   **Course Management**: Professors can create, view, and delete courses.
-   **Enrollment System**: Students can enroll in and drop courses, modeling a many-to-many relationship.
-   **Live API Documentation**: Interactive API documentation with Swagger UI for easy testing and exploration.

---

## 🛠️ Tech Stack & Dependencies

-   **Framework**: [Spring Boot](https://spring.io/projects/spring-boot)
-   **Language**: [Java 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
-   **Security**: Spring Security, JSON Web Tokens (JWT)
-   **API Documentation**: Springdoc OpenAPI (Swagger)
-   **Build Tool**: [Apache Maven](https://maven.apache.org/)
-   **Data Persistence**: Spring Data JPA (compatible with H2, PostgreSQL, MySQL, etc.)

---

## 🔑 Core Concepts & Data Model

The application is built around a relational data model that reflects a typical university structure.

-   **User**: The base entity for authentication. Contains credentials and a role (`STUDENT` or `PROFESSOR`).
-   **Professor / Student**: Profile entities linked to a User via a **One-to-One** relationship.
-   **Course**: Created and managed by Professors. A `Professor` can have many `Courses` (**One-to-Many**).
-   **Enrollment**: The join entity that connects `Students` and `Courses`. This models the **Many-to-Many** relationship where a student can enroll in many courses, and a course can have many students.

---
## 📜 API Documentation & Testing with Swagger

This project includes Swagger UI for live, interactive API documentation and testing. It automatically generates a user-friendly interface from the codebase.

### How to Access

Once the application is running, you can access the Swagger UI in your browser at:

**`http://localhost:8080/swagger-ui.html`**


### Testing Secured Endpoints

Most endpoints are protected and require a JSON Web Token (JWT). To test them using Swagger UI, follow these steps:

1.  Navigate to the `/signin` endpoint under the **User & Authentication** section.
2.  Expand it and click **"Try it out"**. Enter the credentials for a registered user and click **Execute**.
3.  Copy the `token` from the response body.
4.  At the top right of the page, click the **Authorize** button.
5.  In the dialog box, paste your token in the format `Bearer <your_token>` (e.g., `Bearer eyJhbGciOiJI...`) and click **Authorize**.
6.  You can now access all the protected endpoints using the Swagger UI.

---

## 🔐 Security Model: JWT & Role-Based Authorization

The API is secured using JWT. Access to endpoints is restricted based on the user's role, which is embedded within the JWT payload.

-   **Authentication**: Users must call the `/signin` endpoint with their credentials to receive a JWT.
-   **Authorization**: This token must be included in the `Authorization` header of all subsequent requests as a Bearer token (`Authorization: Bearer <your_jwt>`).

### Authorization Rules:

-   **Public Endpoints**: `/register`, `/signin` are accessible to everyone.
-   **Authenticated Endpoints**: All list and view endpoints are accessible to any user with a valid JWT (`STUDENT` or `PROFESSOR`).
-   **Professor-Only Endpoints**: Operations like creating/deleting courses are restricted to users with the `PROFESSOR` role.
-   **Student-Only Endpoints**: Operations like enrolling/dropping a course are restricted to users with the `STUDENT` role.

---
## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing.

### Prerequisites

-   Java Development Kit (JDK) 8 or higher.
-   Apache Maven 3.2+.
-   An IDE like IntelliJ IDEA or VS Code (optional but recommended).

### Installation & Setup

1.  **Clone the repository:**
    ```sh
    git clone [https://github.com/your-username/university-course-management.git](https://github.com/your-username/university-course-management.git)
    cd university-course-management
    ```

2.  **Run the application:**
    The following command will build the project and start the Spring Boot application.
    ```sh
    mvn clean spring-boot:run
    ```
    The API will be available at `http://localhost:8080`.

3.  **Run tests:**
    To execute the suite of unit and integration tests, run:
    ```sh
    mvn clean test
    ```

4.  **Build the project package:**
    To create a distributable JAR file, run:
    ```sh
    mvn clean install
    ```

---

## 🔐 Security Model: JWT & Role-Based Authorization

The API is secured using JWT. Access to endpoints is restricted based on the user's role, which is embedded within the JWT payload.

-   **Authentication**: Users must call the `/signin` endpoint with their credentials to receive a JWT.
-   **Authorization**: This token must be included in the `Authorization` header of all subsequent requests as a Bearer token (`Authorization: Bearer <your_jwt>`).

### Authorization Rules:

-   **Public Endpoints**: `/register`, `/signin` are accessible to everyone.
-   **Authenticated Endpoints**: All list and view endpoints are accessible to any user with a valid JWT (`STUDENT` or `PROFESSOR`).
-   **Professor-Only Endpoints**: Operations like creating/deleting courses and editing professor profiles are restricted to users with the `PROFESSOR` role.
-   **Student-Only Endpoints**: Operations like enrolling/dropping a course are restricted to users with the `STUDENT` role.

---

## 📖 API Endpoints Documentation

Here is a detailed list of the available API endpoints.

### User & Authentication

| Method | Endpoint         | Description                                                               | Authorization |
| :----- | :--------------- | :------------------------------------------------------------------------ | :------------ |
| `POST` | `/register`      | Registers a new user with a specified role (`STUDENT` or `PROFESSOR`).    | Public        |
| `POST` | `/signin`        | Authenticates a user and returns a JWT and their role.                    | Public        |

### Student Endpoints

| Method | Endpoint                 | Description                                    | Authorization |
| :----- | :----------------------- | :--------------------------------------------- | :------------ |
| `GET`  | `/students/list`         | Returns a list of all student profiles.        | Authenticated |
| `GET`  | `/students/view/{id}`    | Returns the details of a specific student.     | Authenticated |
| `PUT`  | `/students/edit/{id}`    | Allows a student to update their own profile.  | `STUDENT`     |

### Professor Endpoints

| Method | Endpoint                  | Description                                      | Authorization |
| :----- | :------------------------ | :----------------------------------------------- | :------------ |
| `GET`  | `/professors/list`        | Returns a list of all professor profiles.        | Authenticated |
| `GET`  | `/professors/view/{id}`   | Returns the details of a specific professor.     | Authenticated |
| `PUT`  | `/professors/edit/{id}`   | Allows a professor to update their own profile.  | `PROFESSOR`   |

### Course Endpoints

| Method   | Endpoint                  | Description                               | Authorization |
| :------- | :------------------------ | :---------------------------------------- | :------------ |
| `POST`   | `/courses/register`       | Allows a professor to create a new course.| `PROFESSOR`   |
| `GET`    | `/courses/list`           | Returns a list of all courses.            | Authenticated |
| `GET`    | `/courses/view/{id}`      | Returns the details of a specific course. | Authenticated |
| `DELETE` | `/courses/delete/{id}`    | Allows a professor to delete a course.    | `PROFESSOR`   |

### Enrollment Endpoints

| Method   | Endpoint                          | Description                                         | Authorization |
| :------- | :-------------------------------- | :-------------------------------------------------- | :------------ |
| `POST`   | `/enrollments/register`           | Allows a student to enroll in a course.             | `STUDENT`     |
| `GET`    | `/enrollments/list/student/{id}`  | Returns all enrollments for a specific student.     | Authenticated |
| `GET`    | `/enrollments/list/course/{id}`   | Returns all enrollments for a specific course.      | Authenticated |
| `DELETE` | `/enrollments/delete/{id}`        | Allows a student to drop a course (delete enrollment). | `STUDENT`     |

---



