Write-Host "Inicio RMI"
java -Djava.security.policy=C:\Users\gasty\Desktop\D_Ej1\Sservidor\servidor.permisos ServidorEco 127.0.0.1 54321
java -Djava.security.policy=C:\Users\gasty\Desktop\D_Ej1\Cliente\cliente.permisos  ClienteEco localhost 54321 funciona
Write-Host "RMI Iniciado - Ejecute Cliente"

