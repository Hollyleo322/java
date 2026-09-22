package controller;


import constant.Constants;
import exception.GameIsAlreadyLaunched;
import exception.GameIsNotLaunched;
import exception.GameWithIdNotFound;
import exception.IncorrectDataInBody;
import lombok.AllArgsConstructor;
import model.WebGameInfo;
import model.WebGameState;
import model.WebUserAction;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.ServerService;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RestController {

  private ServerService service;


  @GetMapping("/games")
  public List<WebGameInfo> getAvailableGames() {
    return service.getAvailableGames();
  }

  @PostMapping("/games/{gameId}")
  public String startGame(@PathVariable Integer gameId) {
    if (gameId < 1 || gameId > 3) {
      throw new GameWithIdNotFound();
    }
    if (service.getId() != 0) {
      throw new GameIsAlreadyLaunched();
    }
    service.setId(gameId);
    service.startGame(gameId);
    return "Game with id " + gameId + " was started";
  }

  @PostMapping("/actions")
  public void doAction(@RequestBody WebUserAction userAction) {
    if (userAction == null || userAction.getAction_id() == null || userAction.getHold() == null
        || userAction.getAction_id() < 0 || userAction.getAction_id() > Constants.ACTION) {
      throw new IncorrectDataInBody();
    }
    if (service.getGameState() == null) {
      throw new GameIsNotLaunched();
    }
    service.doAction(userAction.getAction_id(), userAction.getHold());
  }

  @GetMapping("/state")
  public WebGameState getGameState() {
    if (service.getGameState() == null) {
      throw new GameIsNotLaunched();
    }
    return service.getGameState();
  }

  @GetMapping("/updatestate")
  public void updateState() {
    service.updateState();
  }

  @GetMapping("/reset-race")
  public void resetRace() {
    service.resetRace();
  }

  @GetMapping("/condition-of-race")
  public Integer getCondition() {
    return service.getRaceCondition();
  }

  @GetMapping("/gameid")
  public Integer getGameId() {
    return service.getId();
  }

  @GetMapping("condition-c-game")
  public Integer getCCondition() {
    return service.getConditionCGame();
  }
}
