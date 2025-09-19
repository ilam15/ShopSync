@echo off
REM Batch file to compile and run ShopSync JavaFX application

REM Set JavaFX SDK path - adjust if needed
set JAVAFX_LIB=javafx-sdk-17.0.2\lib

REM Clean previous compiled classes
echo Cleaning previous compiled classes...
rmdir /s /q target\classes

REM Create target\classes directory
mkdir target\classes

REM Compile Java sources
echo Compiling Java sources...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml -d target\classes src\main\java\com\main\MainApp.java src\main\java\com\mall\Mall.java src\main\java\com\shop\Shop.java src\main\java\com\product\Product.java src\main\java\com\customer\Customer.java src\main\java\com\cart\Cart.java src\main\java\com\transaction\Transaction.java src\main\java\com\payment\Payment.java src\main\java\com\customer\Complaint.java

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

REM Run the application
echo Running ShopSync...
java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -cp target\classes com.main.MainApp

pause
