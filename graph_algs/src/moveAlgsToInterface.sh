#!/bin/bash
gradle -p s21_graph_algorithms jar
if ! [[ -d interface/lib ]]
then
    mkdir interface/lib
fi
mv s21_graph_algorithms/build/libs/s21_graph_algorithms-1.0-SNAPSHOT.jar interface/lib
