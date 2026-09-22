#!/bin/bash
gcc -shared -o src/main/resources/MyCWrapper.dll -I"C:/Program Files/Java/jdk-21/include" -I"C:/Program Files/Java/jdk-21/include/win32" src/clib/lib.c -Dwrapper