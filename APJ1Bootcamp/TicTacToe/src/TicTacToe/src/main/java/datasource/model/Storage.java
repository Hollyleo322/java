package datasource.model;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.List;

public class Storage {
    private List<DSCurrentGame> _currentGames;

    public Storage()
    {
        _currentGames = new CopyOnWriteArrayList<>();
    }
    public List<DSCurrentGame> getCurrentGames()
    {
        return _currentGames;
    }
    public void setCurrentGames(List<DSCurrentGame> list)
    {
        _currentGames = list;
    }
    public boolean isHaveUiid(int uiid)
    {
        boolean res = false;
        for (DSCurrentGame cg : _currentGames)
        {
            if (cg.getUuid() == uiid)
            {
                res = true;
            }
        }
        return res;
    }
    public int getIndexOfCurrentGame(int uiid)
    {
        int res = 0;
        for (; res < _currentGames.size(); res++)
        {
            if (_currentGames.get(res).getUuid() == uiid)
            {
                break;
            }
        }
        return res;
    }
}
