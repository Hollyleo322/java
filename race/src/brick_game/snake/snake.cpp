#include "snake.h"
namespace s21 {
Snake::Snake()
    : direction(Up), body({{4, 5}, {5, 5}, {6, 5}, {7, 5}}), apple(0) {
  info = new GameInfo_t;
  if (info) {
    info->field = new int *[HEIGHT];
    if (info->field) {
      for (int i = 0; i < HEIGHT; i++) {
        info->field[i] = new int[WIDTH]{0};
        if (info->field[i] == nullptr) {
          this->~Snake();  // we need call destructor, it was'nt called auto
          throw std::bad_alloc();
        }
      }
      std::ifstream istr("snake_high_score.txt");
      if (istr.is_open() != true) {
        system("echo 0 > snake_high_score.txt");
        istr.open("snake_high_score.txt");
      }
      if (istr.is_open() == true) {
        istr >> info->high_score;
        istr.close();
      } else {  // if opened, we read info, else is 0, we
                // don't care and continue play
        info->high_score = 0;
      }
      info->next = nullptr;
      info->level = 1;
      info->pause = 0;
      info->speed = 1;
      info->score = 0;
    } else {
      delete info;
      throw std::bad_alloc();
    }
  } else {
    throw std::bad_alloc();
  }
}
Snake::~Snake() {
  if (info->field != nullptr) {
    for (int i = 0; i < HEIGHT; i++) {
      if (info->field[i] != nullptr) {
        delete[] info->field[i];
      }
    }
    delete[] info->field;
    info->field = nullptr;
  }
  std::ofstream istr("snake_high_score.txt");
  if (istr.is_open() == true) {
    istr << info->high_score;
    istr.close();
  }
  if (info) {
    delete info;
  }
}
inline void Snake::check_snake(const std::pair<int, int> &end) {
  if ((int)this->apple ==
      this->body.front().first * WIDTH +
          this->body.front().second)  // if apple is inside head
  {
    this->body.push_back(end);
    info->score += 1;
    if (info->score > info->high_score) {
      info->high_score = info->score;
    }
    if (info->score % 5 == 0) {
      change_level();
    }
    this->generate_apple();
  }
  if (this->body.front().first < 0 || this->body.front().first >= HEIGHT ||
      this->body.front().second < 0 ||
      this->body.front().second >= WIDTH)  // if apple is beyond field
  {
    info->pause = Pre_exit_situation;  // end of game
  }
  if (in_body_head() == true)  // if head is inside body
  {
    info->pause = Pre_exit_situation;  // end of game
  }
  if (info->score == 200) {
    info->pause = Win;
  }
}
inline void Snake::to_zero_field() {
  for (int i = 0; i < HEIGHT; i++) {
    for (int j = 0; j < WIDTH; j++) {
      info->field[i][j] = 0;
    }
  }
}
inline void Snake::generate_apple() {
  srand(time(NULL));
  while (in_body_apple()) {
    apple = rand() % (HEIGHT * WIDTH - 1);
  }
}
inline bool Snake::in_body_apple() {
  bool res = false;
  for (auto it = body.begin(); it != body.end(); it++) {
    if ((int)apple == (it->first * WIDTH + it->second)) {
      res = true;
      break;
    }
  }
  return res;
}
inline bool Snake::in_body_head() {
  bool res = false;
  auto head = body.begin();
  for (auto it = (body.begin() + 1); it != body.end(); it++) {
    if (head->first == it->first && head->second == it->second) {
      res = true;
      break;
    }
  }
  return res;
}
extern "C" {
void deleteSnake() {
  if (s21::get_snake()) {
    delete s21::get_snake();
  }
}
void runSnake() {
  s21::Snake *snake_1 = s21::get_snake();
  snake_1->set_field(snake_1->get_body());
}
Snake *get_snake() {
  static Snake snake_game;  // creating static variable
  return &snake_game;
}
void userInput(UserAction_t action, bool hold) {
  Snake *var = get_snake();
  (void)hold;
  char set_dir = 0;
  switch (action) {
    case Up:
      if (var->get_direction() != Down) {
        var->set_direction(Up);
      }
      break;
    case Down:
      if (var->get_direction() != Up) {
        var->set_direction(Down);
      }
      break;
    case Left:
      if (var->get_direction() != Right) {
        var->set_direction(Left);
      }
      break;
    case Right:
      if (var->get_direction() != Left) {
        var->set_direction(Right);
      }
      break;
    case Pause:
      if (var->get_info()->pause == Game) {
        var->get_info()->pause = Pause;  // pause
      } else {
        var->get_info()->pause = Game;  // unpause
      }
      break;
    case Action:
      var->move();
      break;
    case Terminate:
      var->get_info()->pause = End_of_game;  // end of game
      break;
    case Start:
      var->reset_game();
      break;
  }
}
GameInfo_t updateCurrentState() {
  Snake *var = get_snake();
  var->move();
  return var->get_value_info();
}
GameInfo_t get_info_extern() {
  Snake *var = get_snake();
  if (var->get_info()->pause == Change_level) {
    var->get_info()->pause = Game;
  }
  return var->get_value_info();
}
}
void Snake::move() {
  Snake *var = get_snake();
  if (var->get_info()->pause != End_of_game) {
    if (var->get_info()->pause == Change_level) {
      var->get_info()->pause = Game;
    }
    var->to_zero_field();
    auto end = var->get_body().back();
    auto head = var->get_body().front();
    if (var->get_direction() == Up) {
      head.first -= 1;
    } else if (var->get_direction() == Down) {
      head.first += 1;
    } else if (var->get_direction() == Left) {
      head.second -= 1;
    } else if (var->get_direction() == Right) {
      head.second += 1;
    }
    var->get_body().pop_back();
    var->get_body().insert(var->get_body().begin(), head);
    var->check_snake(end);
    if (var->get_info()->pause != Pre_exit_situation) {
      var->set_field(var->get_body());
    }
  }
}
inline void Snake::reset_game() {
  this->to_zero_field();
  info->score = 0;
  info->level = 1;
  info->pause = 0;
  info->speed = 1;
  this->direction = Up;
  body.clear();
  body = {{4, 5}, {5, 5}, {6, 5}, {7, 5}};
  generate_apple();
  set_field(body);
}
void Snake::change_level() {
  if (info->level < 10) {
    info->level += 1;
    info->speed = info->level;
    info->pause = Change_level;
  }
}
}  // namespace s21