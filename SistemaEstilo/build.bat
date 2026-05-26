@echo off
echo Compilando...
mkdir bin 2>nul
dir /s /B src\*.java > fontes.txt
javac -d bin -sourcepath src @fontes.txt
del fontes.txt
if %errorlevel% == 0 (
    echo Sucesso! Executando...
    java -cp bin Main
) else (
    echo Erro na compilacao.
    pause
)
