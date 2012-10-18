#!/bin/sh
rm -Rf /home/ilya/.m2/repository/*
sudo mkdir /opt/temp
sudo wget http://download.java.net/maven/2/javax/sql/jdbc-stdext/2.0/jdbc-stdext-2.0.jar /opt/temp/

mvn -U install:install-file -Dfile=/opt/temp/jdbc-stdext-2.0.jar -DgroupId=javax.sql -DartifactId=jdbc-stdext -Dversion=2.0 -Dpackaging=jar -DgeneratePom=true
mvn exec:java -Dexec.mainClass=rekssoft.task.notebook.AppImpl