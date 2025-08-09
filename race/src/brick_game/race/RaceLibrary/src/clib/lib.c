#include "lib.h"

State initStructure() {
  State state;
  state.field = (int**)malloc(sizeof(int*) * HEIGHT);
  for (int i = 0; i < HEIGHT; i++) {
    state.field[i] = (int*)calloc(WIDTH, sizeof(int));
  }
  state.next = (int**)malloc(sizeof(int*) * WIDTHNEXT);
  for (int i = 0; i < WIDTHNEXT; i++) {
    state.next[i] = (int*)calloc(WIDTHNEXT, sizeof(int));
  }
  state.score = 0;
  state.high_score = 0;
  state.level = 1;
  state.speed = 1;
  state.pause = 1;
  return state;
}
void freeStructure(State* state) {
  for (int i = 0; i < HEIGHT; i++) {
    free(state->field[i]);
  }
  free(state->field);
  for (int i = 0; i < WIDTHNEXT; i++) {
    free(state->next[i]);
  }
  free(state->next);
}
State get_info_extern() {
  State* state = getStatePtr();
  return *state;
}
State* getStatePtr() {
  static State state;
  state = initStructure();
  return &state;
}

void initUserAction(void (*javaCallback)(int), State (*callBackUpdate)()) {
  CallBack* user_action = getCallback();
  user_action->callback = javaCallback;
  user_action->callbackUpdateState = callBackUpdate;
}
CallBack* getCallback() {
  static CallBack user_action;
  return &user_action;
}
void userInput(UserAction action, bool hold) {
  CallBack* user_action = getCallback();
  (user_action->callback)((int)action);
}
State updateCurrentState() {
  CallBack* clb = getCallback();
  (clb->callbackUpdateState)();
}
