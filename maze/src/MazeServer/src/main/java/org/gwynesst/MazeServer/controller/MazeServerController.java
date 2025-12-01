package org.gwynesst.MazeServer.controller;

import java.util.List;
import java.util.Scanner;
import main.demo.alg.Decision;
import main.demo.alg.EllerAlgorithm;
import main.demo.controller.Controller;
import main.demo.model.Field;
import org.gwynesst.MazeServer.exception.NotLoadedField;
import org.gwynesst.MazeServer.model.AutoGenReq;
import org.gwynesst.MazeServer.model.Path;
import org.gwynesst.MazeServer.model.PathRequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import main.demo.model.Point;

@RestController
public class MazeServerController {

  private Field currentField = null;

  @GetMapping("/")
  private ModelAndView getPage() {
    return new ModelAndView("index.html");
  }

  @PostMapping("/upload")
  private Field sendField(@RequestParam("file") MultipartFile file) {
    int rows, cols;
    int[][] vertical, horizontal;
    try (Scanner scanner = new Scanner(file.getInputStream())) {
      rows = scanner.nextInt();
      cols = scanner.nextInt();
      scanner.nextLine();
      vertical = Controller.initArray(rows, cols, scanner);
      scanner.nextLine();
      horizontal = Controller.initArray(rows, cols, scanner);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    currentField = new Field(vertical, horizontal, rows, cols);
    return currentField;
  }

  @PostMapping("/auto-gen")
  private Field sendAutoGenField(@RequestBody AutoGenReq data) {
    return currentField = EllerAlgorithm.getField(data.getRows(), data.getCols());
  }

  @PostMapping("/path")
  private Path getDecision(@RequestBody PathRequestParam params) {
    List<Point> path = null;
    if (currentField != null) {
      Decision decision = new Decision();
      decision.findDecision(currentField,
          new Point(params.getStartedX(), params.getStartedY(), null), new Point(
              params.getFinishedX(), params.getFinishedY(), null));
      path = decision.getPath();
    } else {
      throw new NotLoadedField("Field is not loaded");
    }
    return new Path(path, currentField.getRows(), currentField.getRows());
  }

  @GetMapping("/current-field")
  private Field getCurrentField() {
    if (currentField == null) {
      throw new NotLoadedField("Field is not loaded");
    }
    return currentField;
  }
}
