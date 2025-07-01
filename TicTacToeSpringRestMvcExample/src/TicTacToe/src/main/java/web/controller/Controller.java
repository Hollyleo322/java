package web.controller;

import datasource.constants.Constants;
import datasource.mapper.DataSourceToDomain;
import datasource.mapper.DomainToDataSource;
import datasource.model.DSCurrentGame;
import datasource.repository.DSRepository;
import datasource.repository.RepositoryService;
import datasource.repository.UserRepository;
import domain.model.CurrentGame;
import domain.model.GameField;
import domain.model.Move;
import org.springframework.web.bind.annotation.*;
import web.exception.*;
import web.mapper.WebToDomain;
import web.model.WebCurrentGame;
import web.model.WebGameField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class Controller{

    private final RepositoryService repositoryService;
    private final DSRepository repository;
    private final UserRepository userRepository;

    public Controller(DSRepository repository, RepositoryService repositoryService, UserRepository userRepository) {
        this.repository = repository;
        this.repositoryService = repositoryService;
        this.userRepository = userRepository;
    }


    @PostMapping("/game/{UUID}")
    CurrentGame moveOfSecondPlayer(@RequestBody GameField gameField, @PathVariable int UUID, @RequestHeader("Authorization") String auth) throws UiidNotFoundException, ChangedPreviousMovesException, UiidDoesNotMatchException, IncorrectDataInBodyException, EndOfGame
    {
        DSCurrentGame cg = null;
        try {
            cg = repository.findById(UUID).get();
            if (!repository.findById(UUID).get().getUserId().contains(getId(auth)))
            {
                throw new NotAvailaibleGame();
            }
            if (repositoryService.isChangedPreviousMoves(cg.getUuid(), DomainToDataSource.convertGameFieldtoDS(gameField)))
            {
                throw new ChangedPreviousMovesException();
            }
            cg.setGameField(DomainToDataSource.convertGameFieldtoDS(gameField));
            repository.save(cg);
            if (repositoryService.incorrectData(cg.getUuid()))
            {
                throw new IncorrectDataInBodyException();
            }
            if (cg.isWithAI()) {
                if (repositoryService.isEndOfGame(cg.getUuid()))
                {
                    throw new EndOfGame("You win");
                }
                Move move = repositoryService.getMove(UUID);
                repositoryService.makeMove(move, Constants.AI, cg);
                repository.save(cg);
                if (repositoryService.isEndOfGame(cg.getUuid())) {
                    cg.setCondition(Constants.WIN_OF_AI);
                    repository.save(cg);
                    throw new EndOfGame("AI win");
                }
                if (repositoryService.isTie(UUID)) {
                    cg.setCondition(Constants.TIE);
                    repository.save(cg);
                    throw new EndOfGame("TIE");
                }
            }
            else
            {
                if (cg.getUserId().indexOf(getId(auth)) == 0)
                {
                    cg.setCondition(Constants.MOVE_OF_PLAYER + cg.getUserId().get(1));
                }
                else
                {
                    cg.setCondition(Constants.MOVE_OF_PLAYER + cg.getUserId().get(0));
                }
                repository.save(cg);
                if (repositoryService.isEndOfGame(cg.getUuid()))
                {
                    cg.setCondition(Constants.WIN_OF_PLAYER + getId(auth));
                    repository.save(cg);
                    throw new EndOfGame("Player with id " + getId(auth) + " win");
                }
                if (repositoryService.isTie(UUID)) {
                    cg.setCondition(Constants.TIE);
                    repository.save(cg);
                    throw new EndOfGame("TIE");
                }
            }
        }
        catch (NoSuchElementException e)
        {
            throw new UiidNotFoundException(UUID);
        }
        return DataSourceToDomain.convertCurrentGametoDomain(cg);
    }
    @GetMapping("/info")
    String getInfo()
    {
        return "Move of you is 10, AI is 1, Second player is 20";
    }

    @PostMapping("/create/{uuid}")
    String createGameWithId(@PathVariable Integer uuid, @RequestHeader("Authorization") String info)
    {
        if (repository.findById(uuid).isPresent())
        {
            throw new UiidExistsException(uuid);
        }
        int id = getId(info);
        WebCurrentGame currentGame = new WebCurrentGame(uuid, new WebGameField(), Constants.MOVE_OF_PLAYER + id, true, new ArrayList<>());
        currentGame.getUserId().add(id);
        CurrentGame domainCurrentGame = WebToDomain.convertCurrentGametoDomain(currentGame);
        repository.save(DomainToDataSource.convertCurrentGametoDS(domainCurrentGame));
        return "Game with AI was created with id " + uuid;
    }
    @PostMapping("/createhumansfight/{uuid}")
    String createHumanGameWithId(@PathVariable Integer uuid, @RequestHeader("Authorization") String info) {
        if (repository.findById(uuid).isPresent())
        {
            throw new UiidExistsException(uuid);
        }
        int id = getId(info);
        WebCurrentGame currentGame = new WebCurrentGame(uuid, new WebGameField(), Constants.WAITING_PLAYERS, false, new ArrayList<>());
        currentGame.getUserId().add(id);
        currentGame.setWithHuman();
        CurrentGame domainCurrentGame = WebToDomain.convertCurrentGametoDomain(currentGame);
        repository.save(DomainToDataSource.convertCurrentGametoDS(domainCurrentGame));
        return "Game with another player was created with id " + uuid;
    }
    @GetMapping("/games")
    Collection<CurrentGame> getGames() {
         List<CurrentGame> games = new ArrayList<>();
        repository.findAll().forEach(game -> games.add(DataSourceToDomain.convertCurrentGametoDomain(game)));
        return games;
    }
    @GetMapping("/gameinfo/{uuid}")
    public CurrentGame getGame(@PathVariable Integer uuid) {
        if (repository.findById(uuid).isEmpty()) {
            throw new UiidNotFoundException(uuid);
        }
        return DataSourceToDomain.convertCurrentGametoDomain(repository.findById(uuid).get());
    }
    @GetMapping("/userinfo/{uuid}")
    public String getUserInfo(@PathVariable Integer uuid) {
        if (userRepository.findById(uuid).isEmpty()) {
            throw new UiidNotFoundException(uuid);
        }
        return userRepository.findById(uuid).get().getLogin();
    }
    @PostMapping("/connect/{uuid}")
    public String connect(@PathVariable Integer uuid, @RequestHeader("Authorization") String info) {
        if (repository.findById(uuid).isEmpty()) {
            throw new UiidNotFoundException(uuid);
        }
        DSCurrentGame currentGame = repository.findById(uuid).get();
        if (currentGame.getUserId().size() == 2) {
            return "Max of players already are connected";
        }
        currentGame.getUserId().add(getId(info));
        currentGame.setCondition(Constants.MOVE_OF_PLAYER + currentGame.getUserId().get(0));
        repository.save(currentGame);
        return "You've connected to game with id " + uuid;
    }
    private Integer getId(String info) {
        String[] array = info.split(":");
        return Integer.parseInt(array[1]);
    }

}
