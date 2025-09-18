# ShopSync
The ShopSync is a Java-based application that helps malls manage shops, products, and inventory efficiently. It allows admins and shop managers to track stock levels, update products, handle billing, and generate reports with database support (MySQL/MongoDB).

## Project Structure
This project follows a standard Maven-like directory structure:
```
src/
  main/
    java/com/          # Java source files
    resources/DB/      # Database files and resources
target/
  classes/com/         # Compiled Java classes
javafx-sdk-17.0.2/     # JavaFX SDK
```

## Features

Manage shops and products
Automatic stock updates on sales/restocking
Low-stock alerts and restock requests
Role-based access (Admin, Manager, Staff)
Sales and inventory reports

## Tech Stack

Java(JavaFX for UI)
Database:MySQL

## Build and Run Instructions

### Prerequisites
- Java 17 or higher
- JavaFX SDK 17.0.2 (included in the project)

### Compile the Application
```bash
javac --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -d target/classes src/main/java/com/**/*.java
```

### Run the Application
```bash
java --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.main.MainApp
```

### Alternative: Run with Main.java
```bash
java --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.main.Main
```
