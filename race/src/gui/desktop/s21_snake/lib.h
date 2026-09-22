#pragma once
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "../../../gui/cli/main.h"

typedef struct {
  void (*callbackAction)(UserAction_t action, bool hold);
  GameInfo_t * (*callbackUpdateState)();
  GameInfo_t * (*callbackGetState)();
} Callback;
#ifdef __cplusplus
extern "C" {
#endif
void initUserAction(void (*javaCallback)(UserAction_t action, bool hold),
                    GameInfo_t* (*callBackUpdate)(),
                    GameInfo_t* (*javaCallbackGetState)());
Callback* getCallback();
void startDesktop();
GameInfo_t* getStatePtr();
GameInfo_t initStructure();
void freeStructure(GameInfo_t* state);
GameInfo_t updateCurrentState();
GameInfo_t getInfo();
void userInput(UserAction_t action, bool hold);
void runDesktop(int argc, char** argv);
void startCli();
void runCli();
#ifdef __cplusplus
}
#endif