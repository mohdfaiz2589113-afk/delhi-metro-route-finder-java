@echo off
setlocal
echo ===================================================
echo   Delhi Metro Route & Fare Finder
echo   Zero-Dependency Pure Java (JDK 17+)
echo ===================================================
echo.

rem Check if Java is available or use Eclipse Temurin JDK 17
where javac >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    if exist "C:\Program Files\Eclipse Adoptium\jdk-17.0.21.9-hotspot\bin\javac.exe" (
        set "PATH=C:\Program Files\Eclipse Adoptium\jdk-17.0.21.9-hotspot\bin;%PATH%"
    ) else (
        echo [ERROR] JDK 17+ with javac was not found on your PATH.
        echo Please ensure JDK 17+ is installed.
        pause
        exit /b 1
    )
)

echo [1/2] Compiling Java source files...
if not exist "out" mkdir out
javac -encoding UTF-8 -d out src/model/*.java src/dsa/*.java src/service/*.java src/Main.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERROR] Compilation failed.
    pause
    exit /b 1
)

echo [2/2] Launching HTTP Server on http://localhost:8080 ...
echo Opening browser...
start http://localhost:8080
java -cp out Main
pause
