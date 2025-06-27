@echo off
cd /d "d:\Repos\DOO-T02N-2025\Alunos\Charles Müller\Aula 12"

echo ====================================
echo    TESTE DE COMPILACAO COMPLETO
echo ====================================
echo.

echo Compilando todos os arquivos...
javac *.java

if %ERRORLEVEL% EQU 0 (
    echo ✅ SUCESSO: Todos os arquivos compilados com sucesso!
    echo.
    echo Arquivos disponiveis para execucao:
    echo - java WeatherApp                 ^(Interface Console^)
    echo - java WeatherAppGUI              ^(Interface Grafica Original^)
    echo - java WeatherAppGUI_Fixed        ^(Interface Grafica Melhorada^)
    echo - java WeatherAppLauncher         ^(Launcher Original^)
    echo - java WeatherAppLauncher_Fixed   ^(Launcher Melhorado^)
    echo.
    echo Scripts disponiveis:
    echo - run_console.bat
    echo - run_gui_fixed.bat
    echo - run_launcher.bat
    echo.

) else (
    echo ❌ ERRO: Falha na compilacao!
    echo Verifique os erros acima.
)

echo.
echo Pressione qualquer tecla para continuar...
pause >nul
