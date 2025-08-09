#include "lib.h"
void userInput(UserAction_t action, bool hold) {
  Callback* cb = getCallback();
  (cb->callbackAction)((int)action, hold);
}
GameInfo_t initStructure() {
  GameInfo_t state;
  state.field = (int**)malloc(sizeof(int*) * 20);
  for (int i = 0; i < 20; i++) {
    state.field[i] = (int*)calloc(10, sizeof(int));
  }
  state.next = (int**)malloc(sizeof(int*) * 5);
  for (int i = 0; i < 5; i++) {
    state.next[i] = (int*)calloc(5, sizeof(int));
  }
  state.score = 0;
  state.high_score = 0;
  state.level = 1;
  state.speed = 1;
  state.pause = 1;
  return state;
}
void freeStructure(GameInfo_t* state) {
  if (state->field != NULL) {
    for (int i = 0; i < 20; i++) {
      free(state->field[i]);
    }
    free(state->field);
  }
  if (state->next != NULL) {
    for (int i = 0; i < 5; i++) {
      free(state->next[i]);
    }
    free(state->next);
  }
}
GameInfo_t* getStatePtr() {
  static GameInfo_t state;
  state = initStructure();
  return &state;
}
void initUserAction(void (*javaCallback)(UserAction_t action, bool hold),
                    GameInfo_t* (*callBackUpdate)(),
                    GameInfo_t* (*javaCallbackGetState)()) {
  Callback* cb = getCallback();
  cb->callbackAction = javaCallback;
  cb->callbackUpdateState = callBackUpdate;
  cb->callbackGetState = javaCallbackGetState;
}
Callback* getCallback() {
  static Callback cb;
  return &cb;
}
GameInfo_t updateCurrentState() {
  Callback* cb = getCallback();
  GameInfo_t* result = (*cb->callbackUpdateState)();
  return *result;
}
GameInfo_t getInfo() {
  Callback* cb = getCallback();
  GameInfo_t* result = (*cb->callbackGetState)();
  return *result;
}
void startDesktop() {
  char* argv[] = {"race_desktop"};
  int argc = 1;
  runDesktop(argc, argv);
}
void startCli() { runCli(); }