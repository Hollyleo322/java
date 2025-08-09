#pragma once
typedef enum {
  Start,
  Pause,
  Terminate,
  Left,
  Right,
  Up,
  Down,
  Action
} UserAction_t;

typedef struct {
  int **field;
  int **next;
  int score;
  int high_score;
  int level;
  int speed;
  int pause;
} GameInfo_t;

typedef enum {
  Game = 10,
  Pause_game = 11,
  Error_memory = 12,
  End_of_game = 13,
  Change_level = 14,
  Win = 15,
  Pre_exit_situation = 16
} ConditionGame;