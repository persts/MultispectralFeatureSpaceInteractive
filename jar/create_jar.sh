 #!/bin/bash

FILE_NAME=$1

if [ $# -eq 0 ]
then
  echo "You must specify an output file name"
else
  NAME_SIZE=`echo $FILE_NAME|wc -m`
  let NAME_SIZE=$NAME_SIZE-4 #.jar longitude
  FILE_EXTENSION=`echo $FILE_NAME|cut -c $NAME_SIZE-`
  if [ "FILE_EXTENSION" != ".jar" ]
  then
    FILE_NAME=${FILE_NAME}.jar
  fi

  cd ..
  cd bin
  jar cf $FILE_NAME *
  mv $FILE_NAME ..
  cd ..
  jar xf ./lib/Jama-1.0.2.jar
  jar uf $FILE_NAME ./Jama/*
  rm -r Jama
  rm -r META-INF
  mv $FILE_NAME ./jar
  cd ./jar
  chmod +x $FILE_NAME

fi
