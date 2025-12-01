#!/bin/bash

if ! [[ -d MazeServer/lib ]]
then
    mkdir MazeServer/lib
fi
if ! [[ -f demo/target/*.jar ]]
then
    ./demo/mvnw -f ./demo/pom.xml clean install
fi
cp demo/target/*.jar MazeServer/lib/