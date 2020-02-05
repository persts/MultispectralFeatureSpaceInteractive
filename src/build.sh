#!/bin/bash
javac -source 1.7 -target 1.7 -cp ../lib/Jama-1.0.2.jar:. -d ../bin GUI/*.java
javac -source 1.7 -target 1.7 -cp ../lib/Jama-1.0.2.jar:. -d ../bin DataProvider/*.java
javac -source 1.7 -target 1.7 -cp ../lib/Jama-1.0.2.jar:. -d ../bin Imagery/*.java
javac -source 1.7 -target 1.7 -cp ../lib/Jama-1.0.2.jar:. -d ../bin launcher.java
cp 7band_256x256_example.dat ../bin
cp ../lib/Jama-1.0.2.jar ../bin
cp cbc-blue-sm.jpg ../bin