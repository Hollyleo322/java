WINDOWS_JAVA_HOME="C:/Program Files/Java/jdk-21"
gcc -shared -o src/main/resources/MyLibC.dll -I"$WINDOWS_JAVA_HOME/include" -I"$WINDOWS_JAVA_HOME/include/win32" src/clib/lib.c