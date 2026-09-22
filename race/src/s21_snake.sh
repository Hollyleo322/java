#!/bin/bash
if ! [[ -d brick_game/wrapper_c_games/WrapperCGames/src/main/resources ]]
then
    mkdir brick_game/wrapper_c_games/WrapperCGames/src/main/resources
fi
g++ -shared -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -o brick_game/wrapper_c_games/WrapperCGames/src/main/resources/libs21_snake.so brick_game/snake/snake.cpp -Dsnake