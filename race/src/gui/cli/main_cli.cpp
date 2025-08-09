#include "../../brick_game/snake/snake.h"
#include "../../brick_game/tetris/s21_tetris.h"
extern "C" {
void main_game() {
  int check_condition = 0, new_var = 1, count_periods = 0;
#ifndef race
  int array_periods[] = {30000, 27500, 25000, 22500, 20000,
                         17500, 15000, 12500, 10000, 7500};
#else
  int array_periods[] = {1000, 900, 800, 700, 600, 500, 400, 300, 200, 100};
#endif
  int period = array_periods[count_periods];
#ifdef tetris
  static_storage(1, 1, Up);
#endif
  while (1) {
    check_condition = window_of_game(period, new_var);
    new_var = 0;
    if (check_condition == End_of_game) {
      break;
    } else {
      count_periods = check_condition;
    }
    if (!period) {
      period = array_periods[count_periods];
    }
    period -= 1;
  }
}
}

int main(void) {
  int res = 0;
  if (freopen("/dev/tty", "r", stdin)) {
    initscr();              // Иницилизация ncurses
    nodelay(stdscr, true);  // снимает блокирование getch
    curs_set(0);  // убирает отображение курсора;
    cbreak();
    noecho();  // убирает отображение ввода
#ifdef snake
    try {
      s21::Snake *snake_1 = s21::get_snake();
      snake_1->set_field(snake_1->get_body());
    } catch (std::bad_alloc &e) {
      std::cout << "Allocation failed: " << e.what() << '\n';
    }
#endif
    main_menu();
    endwin();  // закрытие ncurses
  } else {
    res = 1;
    printf("ERROR of freopen!!!\n");
  }
  return res;
}