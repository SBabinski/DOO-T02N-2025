@echo off
REM Script para compilar e executar o Weather App
REM Autor: Charles Müller

echo ====================================
echo    Weather App - Compilacao
echo ====================================
echo.

REM Verificar se Java está instalado
java -version >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERRO: Java nao esta instalado ou nao esta no PATH
    echo Por favor, instale Java 11 ou superior
    pause
    exit /b 1
)

REM Limpar arquivos .class anteriores
echo Limpando arquivos compilados anteriores...
del /Q *.class 2>nul

REM Compilar todas as classes Java
echo Compilando classes Java...
javac *.java

REM Verificar se a compilação foi bem-sucedida
if %ERRORLEVEL% neq 0 (
    echo.
    echo ERRO: Falha na compilacao!
    echo Verifique os erros acima e corrija-os antes de continuar.
    pause
    exit /b 1
)

echo.
echo Compilacao concluida com sucesso!
echo.

REM Perguntar qual interface usar
echo.
echo Escolha a interface desejada:
echo 1. Interface Grafica (Swing)
echo 2. Interface Console
echo 3. Launcher (permite escolher)
echo.
set /p interface="Digite sua escolha (1, 2 ou 3): "

echo.
echo ====================================
echo    Executando Weather App
echo ====================================
echo.

if "%interface%"=="1" (
    echo Iniciando interface grafica...
    java WeatherAppGUI
) else if "%interface%"=="2" (
    echo Iniciando interface console...
    java WeatherApp
) else if "%interface%"=="3" (
    echo Iniciando launcher...
    java WeatherAppLauncher
) else (
    echo Opcao invalida. Iniciando launcher...
    java WeatherAppLauncher
)

echo.
pause
