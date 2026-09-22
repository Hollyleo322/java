package org.example.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.example.model.ResultLists;
import java.util.List;

@NoArgsConstructor
@Slf4j
public class ShiftFileWriter {

  public void writeFiles(ResultLists resultLists, String path, String prefix, CommandLine cmd) {
    if (!path.isEmpty() && (!resultLists.getStrings().isEmpty()) || !resultLists.getIntNumbers()
        .isEmpty() || !resultLists.getDoubleNumbers().isEmpty()) {
      new File(path).mkdirs();
    }
    if (!path.isEmpty()) {
      path += "/";
    }
    if (!resultLists.getDoubleNumbers().isEmpty()) {
      writeFile(path + prefix + "floats.txt",
          resultLists.getDoubleNumbers().stream().map(String::valueOf).toList(), cmd);
    }
    if (!resultLists.getIntNumbers().isEmpty()) {
      writeFile(path  + prefix + "integers.txt",
          resultLists.getIntNumbers().stream().map(String::valueOf).toList(), cmd);
    }
    if (!resultLists.getStrings().isEmpty()) {
      writeFile(path +  prefix + "strings.txt", resultLists.getStrings(), cmd);
    }
  }

  private void writeFile(String file, List<String> list, CommandLine cmd) {
    File resultFile = new File(file);
    boolean append = false;
    if (resultFile.exists() && cmd.hasOption("a")) {
      append = true;
    }
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, append))) {
      for (String it : list) {
        bufferedWriter.write(it);
        bufferedWriter.newLine();
      }
    } catch (IOException e) {
      log.error("Impossible to create FileWriter");
      throw new RuntimeException(e);
    }
  }

}
