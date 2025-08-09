#!/bin/bash
gcc -shared -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux -o gui/desktop/s21_snake/libmyCWrapper.so gui/desktop/s21_snake/lib.c