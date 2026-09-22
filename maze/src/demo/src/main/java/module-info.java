module main.demo {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.desktop;
  requires static lombok;

  opens main.demo to javafx.fxml;
  exports main.demo.controller;
  opens main.demo.controller to javafx.fxml;
  exports main.demo.app;
  opens main.demo.app to javafx.fxml;
  exports main.demo.alg;
  opens main.demo.alg to javafx.fxml;
  exports main.demo.model;
  opens main.demo.model to javafx.fxml;
  exports main.demo.agent;
}