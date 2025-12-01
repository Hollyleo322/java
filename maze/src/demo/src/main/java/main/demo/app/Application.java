package main.demo.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/main/demo/maze.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 900, 650);
    stage.setTitle("Maze");
    stage.setScene(scene);
    stage.setResizable(false);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}