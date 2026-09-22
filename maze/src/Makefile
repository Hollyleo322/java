.PHONY: all install uninstall clean dist run_server dvi tests 

all: install

install:
	./demo/mvnw -f ./demo/pom.xml clean javafx:run
uninstall:
	./demo/mvnw -f ./demo/pom.xml clean
clean: 
	rm -rf demo/target 
dist:
	tar -cf Maze.tar demo MazeServer Makefile
run_server:
	./moveLibrary.sh
	./MazeServer/gradlew -p MazeServer bootRun
dvi:
	makeinfo --dvi manual.texi
tests:
	./demo/mvnw -f ./demo/pom.xml clean jacoco:prepare-agent install jacoco:report