#!/bin/bash
mvn -f mavenJavacpp/pom.xml clean install
mv mavenJavacpp/target/libs/javacpp.jar mavenJavacpp/src/main/java
cd mavenJavacpp/src/main/java
java -jar javacpp.jar structure/Stack.java
java -jar javacpp.jar structure/Queue.java
jar uf javacpp.jar structure/*.class
jar uf javacpp.jar $(find . -name "*.so")
rm -rf structure/*.class
cd ../../../..
if ! [[ -d s21_graph_algorithms/lib ]]; 
then
mkdir s21_graph_algorithms/lib
fi
mv mavenJavacpp/src/main/java/javacpp.jar s21_graph_algorithms/lib
if ! [[ -d interface/lib  ]]
then
mkdir interface/lib
fi
cp s21_graph_algorithms/lib/javacpp.jar interface/lib/ 