cd C:\Users\gasty\Desktop\distribuido\TP2Distribuidos
javac ServerClima.java
javac ServerClimaImp.java
javac PublicadorServerClima.java
start /b java -Djava.security.policy=servidor.permisos PublicadorServerClima
timeout 5
javac Cliente.java
javac Test.java
start /b java -Djava.security.policy=cliente.permisos Test
cd ../..