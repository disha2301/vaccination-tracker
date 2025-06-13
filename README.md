# 🐾 Pet Vaccination Tracker
![Pet Vaccination Tracker UI](https://t4.ftcdn.net/jpg/06/47/28/21/360_F_647282134_hD733iuOcRjHaiqiEjq3DuCNqY3AmHPy.jpg)

A full-stack **Spring Boot REST API** that helps manage pet records and their vaccinations efficiently. Built with **Spring Boot**, **Spring Data JPA**, and **MySQL** (or H2 for testing), the application uses DTO patterns for clean API design, integrates robust validation, and supports full CRUD operations on pets and their associated vaccination history.

---

## 🏗️ Folder Structure
```
src/main/java
└── com.gevernova.petvaccinationtracker
    ├── controller
    │   └── PetController.java
    ├── service
    │   ├── PetService.java
    │   └── PetServiceInterface.java
    ├── entity
    │   ├── Pet.java
    │   └── Vaccination.java
    ├── dto
    │   ├── PetRequestDTO.java
    │   └── PetResponseDTO.java
    │   └── VaccinationDTO.java
    ├── repository
    │   ├── PetRepository.java
    │   └── VaccinationRepository.java
    └── exceptionhandler
        ├── ResourceNotFoundException.java
        └── GlobalExceptionHandler.java
```
---

## 🧪 Tech Stack

| Tool / Framework        | Purpose                                       |
|-------------------------|-----------------------------------------------|
| **Spring Boot**         | Java backend framework                        |
| **Spring Web**          | REST API development                          |
| **Spring Data JPA**     | ORM and database interactions                 |
| **MySQL**               | Persistent relational database                |
| **H2**                  | In-memory DB for quick testing (optional)     |
| **Lombok**              | Reduces boilerplate with annotations          |
| **Jakarta Validation**  | Validates incoming request bodies             |
| **SLF4J + Logback**     | Logging framework                             |
| **Maven**               | Build, manage dependencies, and run app       |

---

## 🚀 Features

- 🐶 Register a new pet with vaccinations
- 📋 Retrieve individual or all pet records
- ✏️ Update existing pet info and their vaccines
- ❌ Delete pets and cascade-delete vaccinations
- 🔍 Search pets by vaccine name

---

## 🔗 API Endpoints

| Method  | Endpoint                         | Description                               |
|---------|----------------------------------|-------------------------------------------|
| `POST`  | `/pets`                          | Register a new pet with vaccination list  |
| `GET`   | `/pets/{id}`                     | Get a pet by ID                           |
| `PUT`   | `/pets/{id}`                     | Update pet and their vaccination records  |
| `DELETE`| `/pets/{id}`                     | Delete a pet and their vaccines           |
| `GET`   | `/pets/by-vaccine?name=Rabies`   | Filter pets by vaccine name               |

---

## 🧾 Sample Request

### POST `/pets`

```json
{
  "petName": "Coco",
  "species": "Cat",
  "breed": "Persian",
  "ownerName": "Disha",
  "ownerContact": "+919876543210",
  "ownerEmail": "da5048@srmist.edu.in",
  "vaccinationList": [
    {
      "vaccine_name": "Rabies",
      "vaccine_date": "2025-06-01"
    },
    {
      "vaccine_name": "Distemper",
      "vaccine_date": "2025-06-10"
    }
  ]
}

```
### 🛠️ DTO & Validation

This project follows the **DTO (Data Transfer Object)** pattern to decouple internal entity models from external API contracts. It also integrates **Jakarta Bean Validation** to ensure that only valid data is processed.

#### 📦 Benefits of Using DTOs
- Prevents overexposing entity fields.
- Facilitates request/response shaping.
- Promotes clean and maintainable code.

#### ✅ Request Validation

We use annotations like `@NotBlank`, `@Pattern`, and `@PastOrPresent` to enforce input constraints and ensure data integrity.

| 🏷️ Field        | ✅ Constraint                    | 📌 Description                                |
|-----------------|----------------------------------|-----------------------------------------------|
| `petName`       | `@NotBlank`                      | Must not be empty or null                     |
| `ownerContact`  | `@Pattern(regexp="^\\d{10}$")`   | Must be a valid 10-digit mobile number        |
| `vaccine_date`  | `@PastOrPresent`                 | Date must be today or in the past             |

#### 🚫 Invalid Input Example

If the user provides invalid data, the API responds with a `400 Bad Request` along with meaningful error messages:

```json
{
  "timestamp": "2025-06-09T10:00:00",
  "status": 400,
  "errors": [
    "Pet name must not be blank",
    "Owner contact must be a valid 10-digit number"
  ]
}
```
### ⚠️ Exception Handling

All exceptions are handled centrally using Spring Boot’s `@ControllerAdvice` and `@ExceptionHandler`. This ensures consistent and informative responses for API consumers while keeping the controller logic clean.

#### 🧰 How It Works
- **`GlobalExceptionHandler.java`**: Intercepts exceptions thrown from anywhere in the application.
- **`ResourceNotFoundException.java`**: Custom exception for handling cases where a requested entity does not exist.
- Automatically returns structured JSON responses for errors.

#### 💥 Commonly Handled Exceptions
| Exception Type              | Trigger Condition                          | HTTP Status |
|-----------------------------|--------------------------------------------|-------------|
| `MethodArgumentNotValidException` | Request body fails validation            | 400         |
| `ResourceNotFoundException`       | Resource (e.g., Pet) not found by ID     | 404         |
| `HttpMessageNotReadableException` | Malformed JSON in request body           | 400         |

#### 📄 Sample Error Response

```json
{
  "timestamp": "2025-06-09T15:34:39",
  "status": 400,
  "errors": [
    "Pet name must not be blank",
    "Owner contact must be valid"
  ]
}
```
---

### 🧠 JPA & Relationships

This project models a **One-to-Many relationship** between `Pet` and `Vaccination`. Each pet can have multiple vaccination records.

#### ✅ Configuration in Entity

```java
@OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Vaccination> vaccinationList = new ArrayList<>();
```
### 🔗 Cascade & Orphan Removal Explained

The `Pet` entity holds a `List<Vaccination>` in a **One-to-Many** relationship. This relationship uses `cascade` and `orphanRemoval` for automated data management.
### 🗃 Database Options

This project supports both **MySQL** for production and **H2** for local development/testing.

---

#### ✅ MySQL (Recommended for Production)

Configure your MySQL database in `application.properties`:

```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/pet_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```
### 🧪 H2 Console (For Quick Testing)
Access the in-memory H2 database at:
```
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave blank)
```
### 📦 How to Run the Application

#### ✅ Prerequisites

Before running the application, ensure the following tools are installed:

- **Java 17** or above
- **Maven** (for building and running the project)
- **MySQL** (optional – can use **H2** in-memory DB for quick testing)

---

#### 🚀 Steps to Run

```bash
# 1. Clone the repository
git clone https://github.com/disha2301/vaccination-tracker.git
cd vaccination-tracker

# 2. (Optional) Configure database in src/main/resources/application.properties

# 3. Run the application using Maven
mvn spring-boot:run
```
### 🔍 Logs Example
```
log.info("Creating new pet record: {}", pet.getPetName());
log.debug("Vaccination added: {}", vaccination.getVaccineName());
log.error("Pet not found with ID: {}", id);
```
## 🙋‍♀️ Author

**Disha**  
Backend Developer | Spring Boot | REST APIs | Java


