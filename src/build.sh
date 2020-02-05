#!/bin/bash
javac -cp ../lib/Jama-1.0.2.jar:. -d ../bin GUI/*.java
javac -cp ../lib/Jama-1.0.2.jar:. -d ../bin DataProvider/*.java
javac -cp ../lib/Jama-1.0.2.jar:. -d ../bin Imagery/*.java
javac -cp ../lib/Jama-1.0.2.jar:. -d ../bin launcher.java
cp 7band_256x256_example.dat ../bin
cp ../lib/Jama-1.0.2.jar ../bin
cp cbc-blue-sm.jpg ../bin