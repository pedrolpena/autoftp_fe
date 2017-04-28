echo off

javac -source 1.7 -target 1.7 -d .\ -cp .\ .\src\autoftp_fe\*.java   
jar cfm AutoFTP_fe.jar manifest.txt autoftp_fe\*.class

IF EXIST .\dist goto deletedist

:deletedist
del /q /s .\dist  > nul
rmdir /q /s .\dist  > nul
:exit

mkdir .\dist
mkdir .\dist\lib
move /y AutoFTP_fe.jar .\dist > nul
del /s /q .\autoftp_fe  > nul
rmdir /s /q .\autoftp_fe  > nul


