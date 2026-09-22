#!/bin/bash
if ! [[ -d brick_game/race/RaceLibrary/src/main/resources ]]
then
    mkdir brick_game/race/RaceLibrary/src/main/resources
fi
gcc -shared -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -o brick_game/race/RaceLibrary/src/main/resources/libMyLibC.so brick_game/race/RaceLibrary/src/clib/lib.c