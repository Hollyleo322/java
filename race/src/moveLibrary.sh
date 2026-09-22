#!/bin/bash
./brick_game/race/RaceLibrary/gradlew -p brick_game/race/RaceLibrary jar
if ! [[ -d brick_game/server/RaceServer/lib ]]
then
    mkdir brick_game/server/RaceServer/lib
fi
cp brick_game/race/RaceLibrary/build/libs/RaceLibrary-1.0-SNAPSHOT.jar brick_game/server/RaceServer/lib