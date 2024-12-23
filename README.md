# eBankify_security_part_1

eBankify is an online banking application designed to manage user accounts, transactions, and administrative tasks securely. The application implements robust authentication mechanisms, including Basic Authentication for administrators and JWT for users and employees.

## Features

- **Admin Authentication**: Basic Auth with BCrypt password encoding.
- **User and Employee Authentication**: JWT-based authentication.
- **Role-based Access Control**: Different permissions for admins, users, and employees.
- **Transaction Management**: Users can create accounts and execute transactions; employees can approve transactions.
- **CORS Validation**: Configured to allow frontend applications from specific origins only.
  
## Authentication Flow

### Admin Authentication (Basic Auth)
- Endpoint: `/admin/login`
- Admin credentials are verified via Basic Authentication and BCrypt password encoding.

### User & Employee Authentication (JWT)
- Endpoint: `/user/login`
- Users and employees authenticate via email and password, receiving a JWT containing their user ID and role.

### Roles & Permissions
- **ADMIN**: Full access to manage users, accounts, and transactions.
- **USER**: Access to personal account creation and transaction history.
- **EMPLOYEE**: Access to customer accounts and transaction approval.

## Setup

### Prerequisites
- Java 11 or later
- Maven
- A PostgreSQL database

### Steps to Run

1. Clone this repository:
   ```bash
   git clone https://github.com/yourusername/ebankify.git
   cd ebankify

2. Set up your database configuration in application.properties or application.yml.
3. Run the project using Maven:
  ```bash
  mvn spring-boot:run
  ```

4. Visit http://localhost:8080 to access the application. 
