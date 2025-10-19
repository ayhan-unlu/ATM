![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template%20Engine-blue)
![License: Free Use](https://img.shields.io/badge/License-Free--Use-green)
![Patika.dev](https://img.shields.io/badge/Patika.dev-Project-blue)
![GitHub last commit](https://img.shields.io/github/last-commit/ayhan-unlu/ATM)

# 💳 ATM Project — Patika.dev Java Bootcamp

> **Status:** ✅ Completed according to Patika.dev ATM Project Requirements  
> Developed using **Spring Boot**, **Thymeleaf**, **Spring Security**, and **MySQL** with **layered architecture**, **OOP**, **SOLID**, and **Design Patterns**.

---

## 🏦 Description

A full-stack **ATM system** simulating real banking operations.  
It provides role-based access for **Admins (Bank)** and **Users (Customers)**, handles money transactions, and logs all activities both in the **database** and **file system**.

The project demonstrates:
- **Spring Boot architecture (Controller → Service → Repository)**
- **Entity–DTO–Mapper** structure using **MapStruct**
- **User authentication & role-based authorization**
- **Input validation & business rules**
- **Custom logging to file (`user_actions.log`)**
- **Pagination for viewing user logs**
- **OOP, Stream API, Optional, Enum, and SOLID principles**

---

## ⚙️ Core Features

### 👤 User (Customer)
- Register & Login (with masked password using BCrypt)
- Deposit money  
- Withdraw money  
- Transfer money to another user  
- View balance  
- View personal transaction logs (paginated)
- Logout safely  

### 🧑‍💼 Admin (Bank)
- View list of all users
- Check each customer’s account details & balance
- View transaction logs for each customer
- Track daily user activities  

---

## 🧩 Technologies Used

| Layer | Technology |
|-------|-------------|
| **Backend** | Spring Boot 3, Java 17 |
| **Frontend** | Thymeleaf, HTML5, CSS3, Bootstrap |
| **Database** | MySQL (JPA / Hibernate) |
| **Security** | Spring Security (BCrypt Password Encoding) |
| **Mapper** | MapStruct |
| **Logging** | Custom File I/O Logging + Console Logs |
| **Architecture** | MVC + Service Layer + DTO/Entity Mapping |
| **Design Pattern** | Singleton (BankHelper), Mapper Pattern |
| **Validation** | Server-side validation with service checks |
| **Pagination** | User log view pagination |

---

## 🗂️ Project Structure
<img width="352" height="890" alt="Screenshot 2025-10-19 at 15 40 19" src="https://github.com/user-attachments/assets/d1b38eea-f5a4-4410-9bed-81a6f61da68c" />

<img width="383" height="461" alt="Screenshot 2025-10-19 at 15 41 31" src="https://github.com/user-attachments/assets/13ea6bab-3271-4bbe-97a5-6552cbac17b2" />

---

## 🧠 OOP & Design Features

- **Encapsulation:** via DTOs and private fields  
- **Abstraction:** service interfaces (e.g. `UserServices`, `UserActionsLogReaderService`)  
- **Inheritance:** layered class hierarchies and interface implementations  
- **Polymorphism:** service interface injection  
- **Design Patterns:** Singleton, Mapper Pattern  
- **Stream API & Optional:** used in filtering and null-safe data operations  
- **Enum:** for role management (Admin/User)

---

## 🔒 Security

- Implemented with **Spring Security**
- Passwords hashed using **BCrypt**
- Role-based access control:  
  - `ADMIN` → full access  
  - `USER` → limited dashboard  
- Session management and logout routes handled securely

---

## 🧰 Getting Started

### Prerequisites
- Java 17+
- Maven 3+
- MySQL running locally
- IntelliJ IDEA or VS Code with Spring Boot plugin

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/ayhan-unlu/ATM.git

🧑‍💻 Test Accounts

| Username | Password | Role                  |
| -------- | -------- | --------------------- |
| `a`      | `a`      | Admin (Drive İzmir)   |
| `b`      | `b`      | Staff (Auto İstanbul) |
| `c`      | `c`      | Customer              |




>><img width="526" height="225" alt="Screenshot 2025-10-18 at 09 52 41" src="https://github.com/user-attachments/assets/f2d8bd88-2c86-4ee6-aa06-094506cdca64" />
>>
>><img width="523" height="240" alt="Screenshot 2025-10-18 at 09 53 01" src="https://github.com/user-attachments/assets/7aed325d-b322-4169-bb3e-532a7114924f" />
>>
>><img width="517" height="211" alt="Screenshot 2025-10-18 at 09 53 16" src="https://github.com/user-attachments/assets/bae0fe08-386c-4383-9bad-c7c9d8b5a3f3" />
>>
>><img width="417" height="256" alt="Screenshot 2025-10-18 at 09 54 12" src="https://github.com/user-attachments/assets/e2f3b440-0075-464f-aa9f-8f69de2ce1d4" />
>>
>><img width="520" height="101" alt="Screenshot 2025-10-19 at 16 02 16" src="https://github.com/user-attachments/assets/c659e376-3e30-4246-8367-65d40196a89f" />

