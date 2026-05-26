#!/bin/bash
echo "Compilando..."
mkdir -p bin
find src -name "*.java" > fontes.txt
javac -d bin -sourcepath src @fontes.txt
rm fontes.txt
if [ $? -eq 0 ]; then
    echo "Sucesso! Executando..."
    java -cp bin Main
else
    echo "Erro na compilacao. Verifique se o JDK esta instalado."
fi
