# ShopSync - Mall Management System

ShopSync is a comprehensive JavaFX-based desktop application designed for efficient mall management. It provides a complete solution for managing shops, products, customers, inventory, billing, and transactions in a mall environment. The application features data persistence, automatic loading of previously stored data on startup, and a user-friendly graphical interface.

## Features

### 🏪 Shop Management
- Add and manage multiple shops within the mall
- Track shop details including ID, name, category, and location
- Automatic persistence of shop data to file system

### 📦 Product Management
- Add products to specific shops with detailed information
- Track product inventory, pricing, and stock levels
- Automatic stock updates during sales transactions
- Product search functionality for customers

### 👥 Customer Management
- Register and manage customer information
- Loyalty points system with automatic earning and redemption
- Customer purchase history tracking
- Complaint registration system

### 🛒 Billing & Transactions
- Comprehensive billing system with cart functionality
- Real-time calculation of subtotals, taxes, and totals
- Multiple payment method support
- Transaction history and invoice generation
- Automatic inventory updates on successful transactions

### 📊 Mall Overview
- Real-time mall statistics and reports
- Overview of all shops, products, and customer activities
- Refreshable dashboard with current mall status

### 💾 Data Persistence
- Automatic saving of all data (customers, shops, products) to text files
- Data loading on application startup - no manual database setup required
- File-based storage in `DB/` directory for easy backup and portability

## Project Structure

```
Java Project/
├── src/main/java/com/
│   ├── main/
│   │   ├── MainApp.java      # Main JavaFX application entry point
│   │   └── Main.java         # Alternative main class
│   ├── mall/
│   │   └── Mall.java         # Mall management logic
│   ├── shop/
│   │   └── Shop.java         # Shop entity and operations
│   ├── product/
│   │   └── Product.java      # Product entity and operations
│   ├── customer/
│   │   ├── Customer.java     # Customer entity and operations
│   │   └── Complaint.java    # Customer complaint system
│   ├── cart/
│   │   └── Cart.java         # Shopping cart functionality
│   ├── transaction/
│   │   └── Transaction.java  # Transaction handling
│   └── payment/
│       └── Payment.java      # Payment processing
├── DB/
│   ├── customers.txt         # Stored customer data
│   ├── shops.txt            # Stored shop data
│   └── products.txt         # Stored product data
├── target/classes/           # Compiled Java classes
├── javafx-sdk-17.0.2/        # JavaFX SDK (included)
├── run_shop_sync.bat         # Batch file to run the application
├── README.md                 # This file
└── TODO.md                   # Project tasks and progress
```

## Tech Stack

- **Language**: Java 17
- **UI Framework**: JavaFX 17.0.2
- **Build Tool**: Manual compilation (javac)
- **Data Storage**: File-based (text files) - No external database required
- **Architecture**: Object-Oriented Design with MVC-like structure

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Windows operating system (batch file provided)
- JavaFX SDK 17.0.2 (included in project)

## Installation & Setup

1. **Clone or Download** the project to your local machine
2. **Ensure Java 17+** is installed and `java`/`javac` are in your PATH
3. **Navigate** to the project directory

## Running the Application

### Method 1: Using the Batch File (Recommended)
Simply double-click `run_shop_sync.bat` or run it from command prompt:
```cmd
.\run_shop_sync.bat
```

### Method 2: Manual Compilation and Run

#### Compile the Application
```bash
javac --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -d target/classes src/main/java/com/main/MainApp.java src/main/java/com/mall/Mall.java src/main/java/com/shop/Shop.java src/main/java/com/product/Product.java src/main/java/com/customer/Customer.java src/main/java/com/cart/Cart.java src/main/java/com/transaction/Transaction.java src/main/java/com/payment/Payment.java src/main/java/com/customer/Complaint.java
```

#### Run the Application
```bash
java --module-path javafx-sdk-17.0.2/lib --add-modules javafx.controls,javafx.fxml -cp target/classes com.main.MainApp
```

## Usage Guide

### Starting the Application
- Run the batch file or use the manual commands above
- The application will automatically load any previously stored data
- Main window with tabbed interface will appear

### Shop Management Tab
- Add new shops with ID, name, and category
- View existing shops in the list
- Shops are automatically saved and loaded

### Product Management Tab
- Select a shop from dropdown
- Add products with ID, name, price, and quantity
- View products for selected shop
- Products are automatically saved and loaded

### Customer Management Tab
- Add new customers with ID, name, and email
- View existing customers
- Customers are automatically saved and loaded

### Billing Tab
- Select customer from dropdown
- Choose shop and search for products
- Add products to cart
- Process payment and generate invoice
- Automatic inventory updates and loyalty points

### Mall Details Tab
- View overall mall statistics
- Refresh to see current data

## Data Persistence

The application automatically saves data to text files in the `DB/` directory:
- `customers.txt`: Customer information and loyalty points
- `shops.txt`: Shop details
- `products.txt`: Product inventory and shop assignments

Data is loaded automatically when the application starts, ensuring no data loss between sessions.

## Key Classes Overview

- **MainApp.java**: Main JavaFX application with UI setup and data loading
- **Mall.java**: Central mall management coordinating shops and operations
- **Shop.java**: Shop entity with product management
- **Product.java**: Product entity with inventory tracking
- **Customer.java**: Customer entity with purchase history and loyalty system
- **Cart.java**: Shopping cart functionality
- **Transaction.java**: Transaction processing and invoice generation
- **Payment.java**: Payment method handling

## Contributing

This is a standalone desktop application. For modifications:
1. Edit the relevant Java files in `src/main/java/com/`
2. Recompile using the batch file or manual commands
3. Test the changes thoroughly

## Troubleshooting

### Compilation Errors
- Ensure Java 17+ is installed
- Check that all source files are present
- Verify JavaFX SDK path in batch file

### Runtime Errors
- Ensure target/classes directory exists
- Check that DB/ directory is writable for data saving
- Verify JavaFX modules are correctly added

### Data Not Loading
- Check that DB/ directory and text files exist
- Ensure file permissions allow reading/writing
- Verify data format in text files matches expected CSV format

## Future Enhancements

- Database integration (MySQL/MongoDB)
- User authentication and role-based access
- Advanced reporting and analytics
- Barcode scanning integration
- Mobile companion app
- Cloud backup functionality

---

**ShopSync** - Streamlining Mall Operations with Modern Java Technology
