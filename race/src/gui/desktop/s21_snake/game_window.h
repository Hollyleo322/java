#ifndef GAME_WINDOW_H
#define GAME_WINDOW_H
#include <QDialog>
#include <QMessageBox>
#include <QPainter>
#include <QPushButton>
#include <QTimer>
#include <QtWidgets>

#include "../../../brick_game/snake/snake.h"
#include "../../../brick_game/tetris/s21_tetris.h"
namespace s21 {
class game_window : public QWidget {
  Q_OBJECT
 private:
  Snake *ptr_snake;
  GameInfo_t *ptr_info;
  QTimer *timer;
#ifdef snake
  int ms = 500;
#else
  int ms = 1000;
#endif

 public:
  game_window(Snake *snake_t, GameInfo_t *ptr_obj, QWidget *parent = nullptr)
      : ptr_snake(snake_t), ptr_info(ptr_obj) {
    timer = new QTimer(this);
    connect(timer, &QTimer::timeout, this, &game_window::update_screen);
#ifndef snake
    this->setWindowIcon(
        QIcon("gui/desktop/s21_snake/resourses/icons/tetris.png"));
#else
    this->setWindowIcon(
        QIcon("gui/desktop/s21_snake/resourses/icons/snake.png"));
#endif
    set_palette();
  }
  void output_game_over();
  void output_pause();
  void paint_added_info(QPainter *painter);
  void start_timer() { timer->start(ms); }
  void set_palette();
  void output_win();

 protected:
  void keyPressEvent(QKeyEvent *event) override;
  void paintEvent(QPaintEvent *event) override;
 private slots:
  void update_screen();
};
}  // namespace s21
#endif  // GAME_WINDOW_H
