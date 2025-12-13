package org.example.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.example.model.ResultLists;

@NoArgsConstructor
@Slf4j
public class FileHandler {

  public ResultLists handleFiles(CommandLine cmd) {
    ResultLists resultLists = new ResultLists(new LinkedList<>(), new LinkedList<>(),
        new LinkedList<>());
    if (cmd.getArgs().length == 0) {
      log.error("No files added, impossible to continue processing files");
      throw new RuntimeException("No files added");
    } else {
      for (int i = 0; i < cmd.getArgs().length; i++) {
        readFile(cmd.getArgs()[i], resultLists);
      }
    }
    return resultLists;
  }

  private void readFile(String filename, ResultLists lists) {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        addStrToList(line, lists);
      }
      log.info("File " + filename + " was processed");
    } catch (FileNotFoundException e) {
      log.warn("File " + filename + " not found");
    } catch (IOException e) {
      log.warn("Buffered reader wasn't be created");
    }
  }

  private void addStrToList(String line, ResultLists lists) {
    try {
      lists.getIntNumbers().add(Integer.parseInt(line));
    } catch (NumberFormatException e) {
      try {
        lists.getDoubleNumbers().add(Double.parseDouble(line));
      } catch (NumberFormatException ex) {
        lists.getStrings().add(line);
      }
    }
  }
}
