@ECHO OFF

set DBFILE=%var1%
echo DB File: %var1%
REM Change working directory to the location of the DB dump file.
C:
CD C:/Users/Ninja/Documents/AltHealthWebAppBackup/
if not exist "restore" mkdir "C:\Users\Ninja\Documents\AltHealthWebAppBackup\restore"

echo decompress DB dump file into  file
Expand %DBFILE% -F:* restore/%DBFILE%

echo Restore selected database
"C:\wamp64\bin\mysql\mysql5.7.14\bin\mysql.exe" --defaults-extra-file=C:\Users\Ninja\git\unisa-project\scripts\my.cnf alt_health < C:/Users/Ninja/Documents/AltHealthWebAppBackup/restore/%DBFILE%
echo DB restore: %DBFILE%

echo Delete uncompressed DB dump file.
C:
CD C:/Users/Ninja/Documents/AltHealthWebAppBackup/restore/
DEL /q /f %DBFILE%

echo %DBFILE%