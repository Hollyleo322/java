package datasource.repository;

import datasource.model.DSCurrentGame;
import datasource.model.Storage;

public class Repository {
    final private Storage _storage;
    public Repository()
    {
        _storage = new Storage();
    }
    public void saveCurrentGame(DSCurrentGame currentGame)
    {
        if (_storage.isHaveUiid(currentGame.getUuid()))
        {
            _storage.getCurrentGames().set(_storage.getIndexOfCurrentGame(currentGame.getUuid()), currentGame);
        }
        else
        {
            _storage.getCurrentGames().add(currentGame);
        }
    }
    public DSCurrentGame getCurrentGame(int uuid)
    {
        if (!_storage.isHaveUiid(uuid))
        {
            return null;
        }
        return  _storage.getCurrentGames().get(_storage.getIndexOfCurrentGame(uuid));
    }
}

