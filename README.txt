*************************************************************************
*                                                                       *
* AutoFTP_fe, a frontend for AutoFTP                                    *
* Copyright (C) 2014  Pedro Pena                                        *
*                                                                       *
* This program is free software: you can redistribute it and/or modify  *
* it under the terms of the GNU General Public License as published by  *
* the Free Software Foundation, either version 3 of the License, or     *
* any later version.                                                    *
*                                                                       * 
* This program is distributed in the hope that it will be useful,       *
* but WITHOUT ANY WARRANTY; without even the implied warranty of        *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
* GNU General Public License for more details.                          *
*                                                                       *
* You should have received a copy of the GNU General Public License     *
* along with this program.  If not, see <http://www.gnu.org/licenses/>. *
*                                                                       *                                         
*************************************************************************



This is a front end for AutoFTP

-----------------------------------------
**COMPILING AND RUNNING THE FTP PROGRAM**
-----------------------------------------

To compile, make sure that a java sdk version of at least 1.7 is installed
and that the jar archive tool is installed.

You can check by typing "javac -version"
you should see something like "javac 1.7.0_65"

Type "jar" and your screen should scroll with many jar options.

When you list the contents of the directory, you should see the following.

 
makeit.bat      - batch script to compile and archive the program 
makeit.sh       - bash  script to compile and archive the program 
manifest.txt    - info that will be added to the manifest in the resulting jar file
README.txt      - this document
src             - directory with source files.


In Windows run makeit.bat in linux/unix run makeit.sh .
In linux/unix you will have to make makeit.sh executable.
To make it executable type

"chmod +x makeit.sh"

When done compiling, the ftp program will be placed in the dist directory.
To run the program enter the dist directory and type.

"java -jar AutoFTP_fe.jar "

If for some reason the script doesn't work you can compile and archive with the following commands.

For linux/unix
"javac -source 1.7 -target 1.7 -d ./ -cp ./ src/autoftp_fe/*.java"
"jar cfm AutoFTP_fe.jar manifest.txt autoftp_fe/*.class"


For Windows
"javac -source 1.7 -target 1.7 -d .\ -cp .\ .\src\autoftp_fe\*.java"   
"jar cfm AutoFTP_fe.jar manifest.txt autoftp_fe\*.class"



to run the program, enter the directory of the created jar file and type

java -jar AutoFTP_fe.jar

Make sure AutoFTP is running first or this program will just hang.




















