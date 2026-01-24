#!/bin/bash

gradle -p s21_graph jar
mv s21_graph/build/libs/s21_graph-1.0-SNAPSHOT.jar s21_graph_algorithms/lib
if ! [[ -d interface/lib ]]
then
    mkdir interface/lib
fi
cp s21_graph_algorithms/lib/s21_graph-1.0-SNAPSHOT.jar interface/lib