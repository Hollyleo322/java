package presentation;

public class StartData {
    private String _name;
    private boolean _isLoad;

    StartData(String name, boolean isLoad) {
        _name = name;
        _isLoad = isLoad;
    }

    public String getName() {
        return _name;
    }

    public boolean IsLoad() {
        return _isLoad;
    }
}
