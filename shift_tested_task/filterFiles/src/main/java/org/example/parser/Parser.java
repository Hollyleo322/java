package org.example.parser;

import java.util.Arrays;
import lombok.NoArgsConstructor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/*
-o -- path
-p -- prefix
-a -- adding to existing files
-s -- short statistic (size of  all lists)
-f -- full statistic (for numbers (float and int) -- min, max, sum and avg, for strings -- quantity, size of short and long)
 */
@NoArgsConstructor
public class Parser {

  public CommandLine getCommandLine(String[] args) {
    CommandLine cmd = null;
    args = correctArgs(args);
    Options options = new Options();
    options.addOption("o", true, "Path where create result files");
    options.addOption("p", true, "Prefix of result files");
    options.addOption("a", false, "Adding to existed result files");
    options.addOption("s", false, "Short statistic");
    options.addOption("f", false, "Full statistic");
    CommandLineParser parser = new DefaultParser();
    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
    }
    return cmd;
  }
  private String[] correctArgs(String[] args) {
    String[] result = new String[args.length + 2];
    int counter = 0;
    for (int i = 0; i < args.length; i++) {
      if (args[i].equals("-o") || args[i].equals("-p") || args[i].equals("-a") || args[i].equals("-s") || args[i].equals("-f")) {
        result[counter++] = args[i];
        if (args[i].equals("-o") || args[i].equals("-p")) {
          if (i + 1 >= args.length || args[i+1].startsWith("-") || args[i+1].matches("^[A-Za-z0-9]+\\.txt$") || args[i+1].matches("^[A-Za-z0-9_/]+/[A-Za-z0-9]+\\.txt$")) {
            result[counter++] = "";
          }else {
            result[counter++] = args[i + 1];
            i++;
          }
        }
      } else if (args[i].matches("^[A-Za-z0-9]+\\.txt$") || args[i].matches("^[A-Za-z0-9_/]+/[A-Za-z0-9]+\\.txt$")){
        result[counter++] = args[i];
      }
    }
    return result;
  }
}
