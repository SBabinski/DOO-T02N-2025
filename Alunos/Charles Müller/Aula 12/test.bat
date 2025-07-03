@echo off
REM Script para testar a compilacao e executar testes
REM Autor: Charles Müller

echo ====================================
echo    Weather App - Testes
echo ====================================
echo.

REM Verificar se Java está instalado
java -version >nul 2>&1
if %ERRORLEVEL% neq 0 (
    echo ERRO: Java nao esta instalado ou nao esta no PATH
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
    pause
    exit /b 1
)

echo.
echo Compilacao concluida com sucesso!
echo.

REM Executar testes
echo ====================================
echo    Executando Testes
echo ====================================
echo.

echo Teste 1: ConfigManager
java ConfigManagerTest

echo.
echo Teste 2: Funcionalidades Gerais
java WeatherAppTest

echo.
echo ====================================
echo    Testes Concluidos
echo ====================================
echo.

REM Verificar arquivos criados
echo Arquivos no diretorio:
dir *.java *.class *.json *.md *.bat 2>nul

echo.
pause
