# Delhi Metro Route & Fare Finder - PowerShell Launch Script
$ErrorActionPreference = "Stop"

Write-Host "===================================================" -ForegroundColor Cyan
Write-Host "  Delhi Metro Route & Fare Finder" -ForegroundColor Yellow
Write-Host "  Zero-Dependency Pure Java (JDK 17+)" -ForegroundColor Green
Write-Host "===================================================" -ForegroundColor Cyan

# Ensure JDK 17 bin is on PATH if needed
if (-not (Get-Command javac -ErrorAction SilentlyContinue)) {
    $temurin = "C:\Program Files\Eclipse Adoptium\jdk-17.0.21.9-hotspot\bin"
    if (Test-Path "$temurin\javac.exe") {
        $env:Path = "$temurin;$env:Path"
    }
}

Write-Host "`n[1/2] Compiling source files..." -ForegroundColor Gray
if (-not (Test-Path "out")) { New-Item -ItemType Directory -Path "out" | Out-Null }
javac -encoding UTF-8 -d out src/model/*.java src/dsa/*.java src/service/*.java src/Main.java

Write-Host "[2/2] Starting server at http://localhost:8080 ..." -ForegroundColor Green
Start-Process "http://localhost:8080"
java -cp out Main
