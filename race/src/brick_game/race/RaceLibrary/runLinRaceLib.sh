JAVAPATH=/usr/lib/jvm/jdk-18
gcc -shared -fPIC -I${JAVAPATH}/include -I${JAVAPATH}/include/linux -o src/main/resources/libMyLibC.so src/clib/lib.c