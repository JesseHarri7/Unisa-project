@ECHO OFF
for /f "tokens=2 delims==" %%a in ('wmic OS Get localdatetime /value') do set "dt=%%a"
set "YY=%dt:~2,2%" & set "YYYY=%dt:~0,4%" & set "MM=%dt:~4,2%" & set "DD=%dt:~6,2%"
set "HH=%dt:~8,2%" & set "Min=%dt:~10,2%" & set "Sec=%dt:~12,2%"

set "datestamp=%YYYY%%MM%%DD%" & set "timestamp=%HH%%Min%%Sec%"
set "fullstamp=%YYYY%-%MM%-%DD%_%HH%-%Min%-%Sec%"
echo fullstamp: "%fullstamp%"

set TIMESTAMP=%fullstamp%

if not exist "C:/Users/Ninja/Documents/AltHealthWebAppBackup" mkdir "C:/Users/Ninja/Documents/AltHealthWebAppBackup"

REM Export all databases into file C:\altHealth\Backups\databases.[year][month][day].sql
"C:\wamp64\bin\mysql\mysql5.7.14\bin\mysqldump.exe" --defaults-extra-file=C:\Users\Ninja\git\unisa-project\scripts\my.cnf alt_health > C:/Users/Ninja/Documents/AltHealthWebAppBackup/altHealthDB.%TIMESTAMP%.sql
echo DB backup: altHealthDB.%TIMESTAMP%.sql
REM Change working directory to the location of the DB dump file.
C:
CD C:/Users/Ninja/Documents/AltHealthWebAppBackup/
REM Compress DB dump file into CAB file (use "EXPAND file.cab" to decompress).
MAKECAB "altHealthDB.%TIMESTAMP%.sql" "altHealthDB.%TIMESTAMP%.sql.cab"
REM Delete uncompressed DB dump file.
DEL /q /f "altHealthDB.%TIMESTAMP%.sql"
echo altHealthDB.%TIMESTAMP%.sql.cab

EXIT