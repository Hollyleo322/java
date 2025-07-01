package web.controller;

import datasource.constants.Constants;
import datasource.mapper.DataSourceToDomain;
import datasource.mapper.DomainToDataSource;
import datasource.model.DSCurrentGame;
import datasource.repository.Repository;
import datasource.repository.RepositoryService;
import domain.model.CurrentGame;
import domain.model.Move;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.web.bind.annotation.*;
import web.exception.*;
import web.mapper.WebToDomain;
import web.model.WebCurrentGame;
import web.model.WebGameField;

@RestController
public class Controller implements BeanFactoryAware {

    private BeanFactory _beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory)
    {
        _beanFactory = beanFactory;
    }

    @PostMapping("/game/{UUID}")
    CurrentGame moveOfBot(@RequestBody CurrentGame currentGame, @PathVariable int UUID) throws UiidNotFoundException, ChangedPreviousMovesException, UiidDoesNotMatchException
    {
        Repository _repository = _beanFactory.getBean(Repository.class);
        RepositoryService _repositoryService = _beanFactory.getBean(RepositoryService.class);
        DSCurrentGame cg = DomainToDataSource.convertCurrentGametoDS(currentGame);
        if (UUID != currentGame.getUuid())
        {
            throw new UiidDoesNotMatchException(UUID, currentGame.getUuid());
        }
        if (_repository.getCurrentGame(cg.getUuid()) == null)
        {
            throw new UiidNotFoundException(cg.getUuid());
        }
        if (_repositoryService.isChangedPreviousMoves(cg.getUuid(), cg.getGameField()))
        {
            throw new ChangedPreviousMovesException();
        }
        _repository.saveCurrentGame(cg);
        if (_repositoryService.incorrectData(cg.getUuid()))
        {
            throw new IncorrectDataInBodyException();
        }
        if (_repositoryService.isEndOfGame(cg.getUuid()))
        {
            throw new EndOfGame("You win");
        }
        Move move = _repositoryService.getMove(UUID);
        _repositoryService.makeMove(move,Constants.AI, cg);
        _repository.saveCurrentGame(cg);
        if (_repositoryService.isEndOfGame(cg.getUuid()))
        {
            throw new EndOfGame("AI win");
        }
        if (_repositoryService.isTie(UUID))
        {
            throw new EndOfGame("TIE");
        }
        return DataSourceToDomain.convertCurrentGametoDomain(cg);
    }
    @PostMapping("/create/{uuid}")
    String createGameWithId(@PathVariable Integer uuid)
    {
        Repository repository = _beanFactory.getBean(Repository.class);
        if (repository.getCurrentGame(uuid) != null)
        {
            throw new UiidExistsException(uuid);
        }
        WebCurrentGame currentGame = new WebCurrentGame(uuid, new WebGameField());
        CurrentGame domainCurrentGame = WebToDomain.convertCurrentGametoDomain(currentGame);
        repository.saveCurrentGame(DomainToDataSource.convertCurrentGametoDS(domainCurrentGame));
        return "Game was created with id " + uuid;
    }
}
