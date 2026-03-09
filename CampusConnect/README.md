# Campus Connect – Student Text Social Platform

A Java Swing + JDBC + MySQL desktop microproject built using MVC architecture.

## Project Structure

```text
CampusConnect/
├── lib/
├── src/
│   └── campusconnect/
│       ├── controller/
│       │   ├── AuthController.java
│       │   ├── CommentController.java
│       │   └── PostController.java
│       ├── database/
│       │   └── DBConnection.java
│       ├── main/
│       │   └── Main.java
│       ├── model/
│       │   ├── Comment.java
│       │   ├── Post.java
│       │   └── User.java
│       └── view/
│           ├── DashboardFrame.java
│           ├── LoginFrame.java
│           ├── PostPanel.java
│           └── RegisterFrame.java
└── database.sql
```

## Features

- Student registration and login.
- Post creation and feed display.
- Like posts.
- Comment on selected posts.
- Search posts by keyword.

## How to Run

1. Install **JDK 8+** and **MySQL**.
2. Run `database.sql` in MySQL Workbench/CLI.
3. Update DB credentials in `DBConnection.java`.
4. Place MySQL Connector JAR inside `lib/`.
5. Compile and run:

```bash
javac -d out -cp "lib/mysql-connector-j-8.0.33.jar" $(find src -name "*.java")
java -cp "out:lib/mysql-connector-j-8.0.33.jar" campusconnect.main.Main
```

On Windows, replace `:` with `;` in classpath.

## Class Explanations (Viva)

- `DBConnection`: Central JDBC connection creator.
- `User`, `Post`, `Comment`: Domain model classes with encapsulated fields.
- `AuthController`: Register/login logic and password hashing.
- `PostController`: Create/list/search/like post operations.
- `CommentController`: Add and fetch comments for a post.
- `LoginFrame`: User login screen.
- `RegisterFrame`: User registration screen.
- `DashboardFrame`: Main application dashboard.
- `PostPanel`: Reusable post card component (username, message, like/comment buttons).
- `Main`: App entry point.

## Suggested Screenshot Layout for Presentation

1. **Login Screen**: app title, username/password, Login/Register buttons.
2. **Registration Screen**: create account form and validation.
3. **Dashboard**: top post composer, middle post feed cards, bottom comment section.
4. **Search Demo**: keyword search showing filtered posts.

