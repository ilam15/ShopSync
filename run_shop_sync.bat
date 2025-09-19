@echo off
REM Batch file to compile and run ShopSync JavaFX application

REM Set JavaFX SDK path - adjust if needed
set JAVAFX_LIB=javafx-sdk-17.0.2\lib

REM Compile Java sources
echo Compiling Java sources...
javac --module-path "%JAVAFX_LIB%" --add-modules javafx.controls,javafx.fxml -d target\classes src\main\java\com\*\*.java

if errorlevel 1 (
    echo Compilation failed.
    pause
    exit /b 1
)

REM Run the application
echo Running ShopSync...
java --module-path %JAVAFX_LIB% --add-modules javafx.controls,javafx.fxml -cp target\classes com.main.MainApp

pause
