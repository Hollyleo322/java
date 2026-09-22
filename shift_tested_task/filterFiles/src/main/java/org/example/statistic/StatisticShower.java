package org.example.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.example.math.MathOperator;
import org.example.model.ResultLists;

@RequiredArgsConstructor
@Slf4j
public class StatisticShower {

  private final MathOperator mathOperator;

  public void showStatistic(ResultLists resultLists, CommandLine cmd) {
    if (cmd.hasOption("s")) {
      showShortStatistic(resultLists);
    }
    if (cmd.hasOption("f")) {
      showFullStatistic(resultLists, cmd);
    }
  }

  private void showShortStatistic(ResultLists resultLists) {
    if (!resultLists.getIntNumbers().isEmpty()) {
      log.info("In file with integers wrote {} values", resultLists.getIntNumbers().size());
    }
    if (!resultLists.getDoubleNumbers().isEmpty()) {
      log.info("In file with floats wrote {} values", resultLists.getDoubleNumbers().size());
    }
    if (!resultLists.getStrings().isEmpty()) {
      log.info("In file with strings wrote {} values", resultLists.getStrings().size());
    }
  }

  private void showFullStatistic(ResultLists resultLists, CommandLine cmd) {
    if (!cmd.hasOption("s")) {
      showShortStatistic(resultLists);
    }
    if (!resultLists.getIntNumbers().isEmpty()) {
      log.info("Integers");
      log.info("Min {}, Max {}, Sum {}, Avg {}", mathOperator.minInt(resultLists.getIntNumbers()),
          mathOperator.maxInt(resultLists.getIntNumbers()),
          mathOperator.sumInt(resultLists.getIntNumbers()),
          mathOperator.avgInt(resultLists.getIntNumbers()));
    }
    if (!resultLists.getDoubleNumbers().isEmpty()) {
      log.info("Doubles");
      log.info("Min {}, Max {}, Sum {}, Avg {}",
          mathOperator.minDouble(resultLists.getDoubleNumbers()),
          mathOperator.maxDouble(resultLists.getDoubleNumbers()),
          mathOperator.sumDouble(resultLists.getDoubleNumbers()),
          mathOperator.avgDouble(resultLists.getDoubleNumbers()));
    }
    if (!resultLists.getStrings().isEmpty()) {
      log.info("String");
      log.info("Short str size {}, Long str size {}",
          mathOperator.sizeOfShortStr(resultLists.getStrings()),
          mathOperator.sizeOfLongStr(resultLists.getStrings()));
    }
  }
}
