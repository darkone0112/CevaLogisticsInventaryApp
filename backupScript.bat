@echo off
For /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c-%%b-%%a)
For /f "tokens=1-2 delims=/:" %%a in ("%TIME%") do (set mytime=%%a%%b)

set BACKUP_FILE="C:\inventoryBackup\mydb_%mydate%_%mytime%.sql"
set MYSQL_USER="vscode"
set MYSQL_PASSWORD="2458"
set DATABASE_NAME="inventary"
set LOG_FILE="C:\inventoryBackup\mydb_%mydate%_%mytime%.log"

"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysqldump.exe" -u %MYSQL_USER% -p%MYSQL_PASSWORD% %DATABASE_NAME% > %BACKUP_FILE% 2> %LOG_FILE%
