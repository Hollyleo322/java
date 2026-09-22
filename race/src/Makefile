cppflags=g++ -Wall -Wextra -Werror --std=c++17 -o
cflags=gcc -Wall -Wextra -Werror -std=c11 -o
.PHONY: test snake_desktop s21_snake.a s21_tetris.a race_desktop
install: snake_desktop snake_cli tetris_desktop tetris_cli
uninstall: clean
s21_snake.a:
	g++ -c brick_game/snake/snake.cpp -o brick_game/snake/s21_snake.o -Dsnake
	ar rcs gui/desktop/s21_snake/s21_snake.a brick_game/snake/s21_snake.o
	rm -rf brick_game/snake/*.o
s21_snake.so:
	./s21_snake.sh
s21_tetris.so:
	./s21_tetris.sh
s21_tetris.a:
	gcc -c brick_game/tetris/s21_tetris.c -o brick_game/tetris/s21_tetris.o
	ar rcs gui/desktop/s21_snake/s21_tetris.a brick_game/tetris/s21_tetris.o
	rm -rf brick_game/tetris/*.o
wrapper_c_games_lib: s21_snake.so s21_tetris.so
	./brick_game/wrapper_c_games/WrapperCGames/gradlew -p brick_game/wrapper_c_games/WrapperCGames/ jar
	cp brick_game/wrapper_c_games/WrapperCGames/build/libs/WrapperCGames-1.0-SNAPSHOT.jar brick_game/server/RaceServer/lib
tetris_desktop: s21_tetris.a 
	cmake gui/desktop/s21_snake -B tetris_desktop -Dsnake=OFF
	$(MAKE) -C tetris_desktop
tetris_cli: s21_tetris.a
	mkdir tetris_cli
	gcc -c gui/cli/gui.c -o gui/cli/gui.o -Dtetris
	g++ -c gui/cli/main_cli.cpp -o gui/cli/main_cli.o -Dtetris
	g++ -o game gui/cli/gui.o gui/cli/main_cli.o -lncurses ./gui/desktop/s21_snake/s21_tetris.a -Dtetris
	rm -rf gui/cli/*.o
	mv game tetris_cli
	./tetris_cli/game
snake_desktop: s21_snake.a
	cmake gui/desktop/s21_snake -B snake_desktop -Dsnake=ON
	$(MAKE) -C snake_desktop
s21_race.so :
	./runLinLib.sh
desktop_for_race: s21_race.so
	cmake gui/desktop/s21_snake -B race_desktop -Drace=ON -Dsnake=OFF
	$(MAKE) -C race_desktop
race_lib:
	./runLinRaceLib.sh
	./moveLibrary.sh
run_server: race_lib wrapper_c_games_lib
	./brick_game/server/RaceServer/gradlew -p brick_game/server/RaceServer bootRun
race_desktop: desktop_for_race
	./mkdirToWrapper.sh
	mv race_desktop/libs21_race_desktop.so brick_game/wrapper/Wrapper/src/main/resources
	./brick_game/wrapper/Wrapper/gradlew -p brick_game/wrapper/Wrapper/ shadowJar
	java -cp brick_game/wrapper/Wrapper/build/libs/Wrapper-1.0-SNAPSHOT-all.jar run.Desktop
test_race:
	./runLinRaceLib.sh
	./brick_game/race/RaceLibrary/gradlew -p brick_game/race/RaceLibrary/ test
	./brick_game/race/RaceLibrary/gradlew -p brick_game/race/RaceLibrary/ jacocoTestReport
snake_cli: s21_snake.a
	mkdir snake_cli
	gcc -c gui/cli/gui.c -o gui/cli/gui.o -Dsnake
	g++ -c gui/cli/main_cli.cpp -o gui/cli/main_cli.o -Dsnake
	g++ -o game gui/cli/gui.o gui/cli/main_cli.o ./gui/desktop/s21_snake/s21_snake.a -lncurses -Dsnake
	rm -rf gui/cli/*.o
	mv game snake_cli
	./snake_cli/game
race_cli: s21_race.so
	gcc -fPIC -c gui/cli/gui.c -o gui/cli/gui.o -Drace
	g++ -fPIC -c gui/cli/main_cli.cpp -o gui/cli/main_cli.o -Drace
	gcc -fPIC -c gui/desktop/s21_snake/lib.c -o gui/desktop/s21_snake/lib.o -Drace
	g++ -shared -o librace_cli.so gui/cli/gui.o gui/cli/main_cli.o gui/desktop/s21_snake/lib.o -lncurses -Drace
	rm -rf gui/cli/*.o gui/desktop/s21_snake/*.o
	./mkdirToWrapper.sh
	mv librace_cli.so brick_game/wrapper/Wrapper/src/main/resources
	./brick_game/wrapper/Wrapper/gradlew -p brick_game/wrapper/Wrapper/ shadowJar
	java -cp brick_game/wrapper/Wrapper/build/libs/Wrapper-1.0-SNAPSHOT-all.jar run.Cli --enable-native-access=ALL-UNNAMED
clean:
	rm -rf snake_desktop gui/desktop/s21_snake/*.a tetris_desktop gui/cli/*.o *.o tetris_cli brick_game/snake/*.o *.o snake_cli *.gcda *.gcno *.info test_gcov report test s21_snake
test: s21_snake.a
	$(cppflags) test test.cpp ./gui/desktop/s21_snake/s21_snake.a -lgtest
	./test
gcov_report:
	$(cppflags) test_gcov --coverage test.cpp brick_game/snake/snake.cpp -lgtest
	./test_gcov
	lcov -t "name" -o s21_snake.info -c -d . --exclude "/usr/include/*" --ignore mismatch
	genhtml -o report s21_snake.info 
style:
	clang-format -n --style=google *.cpp brick_game/snake/*.cpp brick_game/tetris/*.c gui/cli/*.c gui/cli/*.cpp gui/desktop/s21_snake/*.cpp
format:
	clang-format -i --style=google *.cpp brick_game/snake/*.cpp brick_game/tetris/*.c gui/cli/*.c gui/cli/*.cpp gui/desktop/s21_snake/*.cpp
dist:
	tar -cf brick_game2.tar brick_game gui Makefile
dvi:
	makeinfo --dvi manual.texi
html:
	makeinfo --html manual.texi
