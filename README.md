# sgd


## 📝 Description

The 'sgd' project is a financial management system designed to help users organize and control their money. It allows you to register accounts and users, manage bank transactions, create and track installment plans, and ensure secure authentication using JWT. The system also integrates with RabbitMQ to process financial and user-related events asynchronously, ensuring scalability and reliability.

## 🛠️ Tech Stack

- ☕ Java (Maven)
- PostgresSQL
- RabbitMQ

## 📁 Project Structure

```
.
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── br
    │   │       └── com
    │   │           └── adailtonskywalker
    │   │               └── sgd
    │   │                   ├── SgdApplication.java
    │   │                   ├── config
    │   │                   │   ├── ApplicationConfig.java
    │   │                   │   ├── CustomAccessDeniedHandler.java
    │   │                   │   ├── CustomAuthenticationEntryPoint.java
    │   │                   │   ├── JwtAuthenticationFilter.java
    │   │                   │   ├── RabbitMQConfig.java
    │   │                   │   ├── SecurityConfig.java
    │   │                   │   └── queues
    │   │                   │       ├── TransactionQueueConfig.java
    │   │                   │       └── UserQueueConfig.java
    │   │                   ├── consumers
    │   │                   │   ├── TransactionCreatedConsumer.java
    │   │                   │   └── UserCreatedConsumer.java
    │   │                   ├── controller
    │   │                   │   ├── AuthController.java
    │   │                   │   ├── TransactionController.java
    │   │                   │   └── UserController.java
    │   │                   ├── dto
    │   │                   │   ├── AccountRequestData.java
    │   │                   │   ├── AccountResponseData.java
    │   │                   │   ├── InstallmentPlanRequestData.java
    │   │                   │   ├── InstallmentRequestData.java
    │   │                   │   ├── LoginRequestData.java
    │   │                   │   ├── LoginResponseData.java
    │   │                   │   ├── TransactionRequestData.java
    │   │                   │   ├── TransactionResponseData.java
    │   │                   │   ├── UserRequestData.java
    │   │                   │   └── UserResponseData.java
    │   │                   ├── interfaces
    │   │                   │   └── Mapper.java
    │   │                   ├── mapper
    │   │                   │   ├── AccountMapper.java
    │   │                   │   ├── TransactionMapper.java
    │   │                   │   └── UserMapper.java
    │   │                   ├── messaging
    │   │                   │   ├── EventQueue.java
    │   │                   │   └── Queues.java
    │   │                   ├── model
    │   │                   │   ├── Account.java
    │   │                   │   ├── Installment.java
    │   │                   │   ├── InstallmentPlan.java
    │   │                   │   ├── Transaction.java
    │   │                   │   ├── TransactionType.java
    │   │                   │   └── User.java
    │   │                   ├── repository
    │   │                   │   ├── AccountRepository.java
    │   │                   │   ├── InstallmentPlanRepository.java
    │   │                   │   ├── InstallmentRepository.java
    │   │                   │   ├── TransactionRepository.java
    │   │                   │   └── UserRepository.java
    │   │                   ├── sender
    │   │                   │   └── Sender.java
    │   │                   └── service
    │   │                       ├── AccountService.java
    │   │                       ├── AuthService.java
    │   │                       ├── InstallmentPlanService.java
    │   │                       ├── InstallmentService.java
    │   │                       ├── JwtService.java
    │   │                       ├── TransactionService.java
    │   │                       └── UserService.java
    │   └── resources
    │       └── application.yml
    └── test
        └── java
            └── br
                └── com
                    └── adailtonskywalker
                        └── sgd
                            └── SgdApplicationTests.java
```

## 🛠️ Development Setup

### Java (Maven) Setup
1. Install Java (JDK 11+ recommended)
2. Install Maven
3. Install dependencies: `mvn install`
4. Run the project: `mvn exec:java` or check `pom.xml` for specific run commands


## 👥 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork** the repository
2. **Clone** your fork: `git clone https://github.com/amskywalker/sgd.git`
3. **Create** a new branch: `git checkout -b feature/your-feature`
4. **Commit** your changes: `git commit -am 'Add some feature'`
5. **Push** to your branch: `git push origin feature/your-feature`
6. **Open** a pull request

Please ensure your code follows the project's style guidelines and includes tests where applicable.

---
*This README was generated with ❤️ by ReadmeBuddy*
