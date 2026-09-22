#!/bin/bash
JAVAPATH=/usr/lib/jvm/jdk-18
gcc -shared -fPIC -I${JAVAPATH}/include -I${JAVAPATH}/include/linux -o libmyCWrapper.so src/clib/lib.c