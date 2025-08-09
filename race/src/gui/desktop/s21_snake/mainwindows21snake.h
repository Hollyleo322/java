#ifndef MAINWINDOWS21SNAKE_H
#define MAINWINDOWS21SNAKE_H
#include <QDialog>
#include <QPushButton>
#include <QtWidgets>

#include "../../../brick_game/snake/snake.h"
#include "game_window.h"
namespace s21 {
class MainWindowS21Snake : public QWidget {
  Q_OBJECT

 public:
  MainWindowS21Snake(GameInfo_t *ptr_obj, Snake *ptr_snake,
                     QWidget *parent = nullptr);
  ~MainWindowS21Snake() = default;
  game_window *get_game_window() { return ptr_game; }
  void set_logo(QVBoxLayout *layout);
  void set_qlabel(QVBoxLayout *layout);
  void set_palette();
  void set_buttons(QVBoxLayout *layout);
  template <typename T>
  static void set_in_middle_widgets(T *obj) {
    QRect screenGeometry = QGuiApplication::primaryScreen()
                               ->geometry();  // прямоугольник экрана, в котором
                                              // будет отображаться окно
    int x = (screenGeometry.width() - obj->width()) /
            2;  // Вычисляем координату X для центрирования
    int y = (screenGeometry.height() - obj->height()) /
            2;  // Вычисляем координату Y для центрирования
    obj->move(x, y);
  }

 private:
  GameInfo_t *ptr_game_info;
  QPushButton *start_game;
  QPushButton *input_settings;
  QPushButton *exit;
  game_window *ptr_game;
  QVBoxLayout *layout;
 private slots:
  void start_game_window();
  void output_settings();
 public slots:
  void delete_main_window() { this->deleteLater(); }
};
}  // namespace s21
#endif  // MAINWINDOWS21SNAKE_H
