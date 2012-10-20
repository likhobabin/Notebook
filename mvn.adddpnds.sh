#!/bin/sh
FILE=./jdbc-stdext-2.0.jar

if [ ! -f $FILE ]
then
    echo "File $FILE does not exists"
    wget http://download.java.net/maven/2/javax/sql/jdbc-stdext/2.0/jdbc-stdext-2.0.jar 
fi

mvn -U install:install-file -Dfile=./jdbc-stdext-2.0.jar -DgroupId=javax.sql -DartifactId=jdbc-stdext -Dversion=2.0 -Dpackaging=jar -DgeneratePom=true
