#pragma once
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#define WIDTH 10
#define HEIGHT 20
#define WIDTHNEXT 5
typedef struct {
  int** field;
  int** next;
  int score;
  int high_score;
  int level;
  int speed;
  bool pause;
} State;
typedef struct {
  void (*callback)(int);
  State (*callbackUpdateState)();
} CallBack;
typedef enum UserAction {
  Start,
  Pause,
  Terminate,
  Left,
  Right,
  Up,
  Down,
  Action
} UserAction;
void userInput(UserAction action, bool hold);
void initUserAction(void (*javaCallback)(int), State (*callBackUpdateState)());
State updateCurrentState();
void output(State* state);
State get_info_extern();
State* getStatePtr();
CallBack* getCallback();