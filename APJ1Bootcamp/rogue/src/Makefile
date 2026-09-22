compile:
	javac -d build -cp "./jcurses/lib/*" Main.java domain/* presentation/* datalayer/*
run:
	java -cp "./build:./jcurses/lib/*" Main

make_reference:
	ln -s /usr/lib/x86_64-linux-gnu/libncurses.so.6.3 /usr/lib/x86_64-linux-gnu/libncurses.so.5
make_lib_path:
	LD_LIBRARY_PATH=/mnt/d/jcursesunix/jcurses/lib/
test:
	javac -d build -cp "./libs/*" Test.java TestRoom.java domain/*
test_run:
	java -cp "./build:./libs/*" Test
clean:
	rm -rf build/*
