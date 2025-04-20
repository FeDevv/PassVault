@echo off
where java >nul 2>nul
IF %ERRORLEVEL% NEQ 0 (
    echo ❌ Java non è installato o non è aggiunto al PATH.
    pause
    exit /b
)

java -jar PassVault.jar

pause
