@echo off
REM Navigate to the directory of the script
cd %~dp0

REM Create bin directory if it doesn't exist
if not exist bin (
    mkdir bin
)

REM Compile all Java files
javac -d bin *.java

REM Check if compilation succeeded
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)

REM Copy resources to bin directory
xcopy resources bin/resources /E /I /Y

REM Run the main class
java -cp bin AlphaCheck

REM Pause to keep the command prompt open after execution
pause
