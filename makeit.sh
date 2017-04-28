#!/bin/bash
javac -source 1.7 -target 1.7 -d ./ -cp ./ src/autoftp_fe/*.java 
jar cfm AutoFTP_fe.jar manifest.txt autoftp_fe/*.class 
if [ -d "dist" ]; then
    rm -r dist
fi
mkdir ./dist
rm -r ./autoftp_fe
mv ./AutoFTP_fe.jar ./dist


