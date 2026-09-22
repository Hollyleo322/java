#include "turnOnFromJava.h"
void runDesktop(int argc, char* argv[]) {
  QApplication* app = new QApplication(argc, argv);
  GameInfo_t info = getInfo();
  s21::MainWindowS21Snake* desktop =
      new s21::MainWindowS21Snake(&info, nullptr);
  desktop->show();
  app->exec();
  delete desktop;
}
