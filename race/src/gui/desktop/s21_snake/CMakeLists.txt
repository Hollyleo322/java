cmake_minimum_required(VERSION 3.5)

project(desktop VERSION 0.1 LANGUAGES CXX)

set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTORCC ON)
option(snake "snake enabled" ON)
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_INCLUDE_CURRENT_DIR ON)
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -fPIC -fvisibility=default")
find_package(Qt6 CONFIG REQUIRED COMPONENTS Widgets)


if (snake)
    project(s21_snake VERSION 0.1 LANGUAGES CXX)
    add_executable(s21_snake
    game_window.cpp
    main_desktop.cpp
    mainwindows21snake.cpp
)
    target_link_libraries(s21_snake
        Qt6::Widgets)
    add_definitions(-Dsnake)
    target_link_libraries(s21_snake ${CMAKE_CURRENT_SOURCE_DIR}/s21_snake.a)
elseif(race)
    project(s21_race VERSION 0.1 LANGUAGES CXX C)
    add_definitions(-Drace)
    add_library(s21_race_desktop SHARED
    game_window.cpp
    mainwindows21snake.cpp
    turnOnFromJava.cpp
    )
    target_compile_options(s21_race_desktop PRIVATE -fPIC)
    target_link_libraries(s21_race_desktop
        Qt6::Widgets)
    target_link_libraries(s21_race_desktop ${CMAKE_CURRENT_SOURCE_DIR}/libmyCWrapper.so)
else()
    project(s21_tetris VERSION 0.1 LANGUAGES CXX)
    add_executable(s21_tetris
    game_window.cpp
    main_desktop.cpp
    mainwindows21snake.cpp
    )
    add_definitions(-Dtetris)
    target_link_libraries(s21_tetris
        Qt6::Widgets)
    target_link_libraries(s21_tetris ${CMAKE_CURRENT_SOURCE_DIR}/s21_tetris.a)
endif()
