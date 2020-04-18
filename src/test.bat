cd C:\Users\gasty\Desktop\distribuido\TP2Distribuidos
javac Calculadora.java
javac CalculadoraImp.java
javac Servidor.java
start /b java -Djava.security.policy=servidor.permisos Servidor 54321
cd C:\Users\gasty\Desktop\distribuido\TP2Distribuidos
timeout 5
javac Calculadora.java
javac Cliente.java
start /b java -Djava.security.policy=cliente.permisos Cliente
cd ../..