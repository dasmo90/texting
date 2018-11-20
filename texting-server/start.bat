@echo off
set ip_address_string="IPv4-Adresse"
echo Network Connection Test
for /f "usebackq tokens=2 delims=:" %%f in (`ipconfig ^| findstr /c:%ip_address_string%`) do set ip_address=%%f
set ip_address=%ip_address:~1%
echo ^<html^>^<body^>Enter ^<p^>http://%ip_address%:8080^</p^> in your browser.^</body^>^</html^> > index.html
start "" index.html
java -jar target\texting-server-1.0-SNAPSHOT.jar