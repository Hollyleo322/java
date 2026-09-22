package org.example;

import org.example.handler.FileHandler;
import org.example.math.MathOperator;
import org.example.parser.Parser;
import org.example.statistic.StatisticShower;
import org.example.writer.ShiftFileWriter;

// if some list is empty, not create file
public class Main {

  public static void main(String[] args) {
    Parser parser = new Parser();
    var cmd = parser.getCommandLine(args);
    FileHandler fileHandler = new FileHandler();
    var lists = fileHandler.handleFiles(cmd);
    ShiftFileWriter fileWriter = new ShiftFileWriter();
    String prefix = "", path = "";
    if (cmd.hasOption("o")) {
      if (cmd.getOptionValue("o") != null) {
        path = cmd.getOptionValue("o");
      }
    }
    if (cmd.hasOption("p")) {
      if (cmd.getOptionValue("p") != null) {
        prefix = cmd.getOptionValue("p");
      }
    }
    fileWriter.writeFiles(lists, path, prefix, cmd);
    if (cmd.hasOption("f") || cmd.hasOption("s")) {
      StatisticShower statisticShower = new StatisticShower(new MathOperator());
      statisticShower.showStatistic(lists, cmd);
    }
  }
}