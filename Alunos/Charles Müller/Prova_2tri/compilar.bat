@echo off
chcp 65001 >nul
echo Compilando Sistema de Series TV...
javac -encoding UTF-8 *.java
if %errorlevel% == 0 (
    echo Compilacao concluida com sucesso!
    echo.
    echo Para executar o sistema, use: java SistemaSeriesTV
) else (
    echo Erro na compilacao!
)
pause