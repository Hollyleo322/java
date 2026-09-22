package web.model;

public class StatisticWithName {
    private String uuidWithLogin;
    private double ratio;

    public StatisticWithName(String uuidWithLogin, double ratio) {
        this.uuidWithLogin = uuidWithLogin;
        this.ratio = ratio;
    }

    public String getUuidWithLogin(){
        return uuidWithLogin;
    }

    public double getRatio(){
        return ratio;
    }

    public void setUuidWithLogin(String uuidWithLogin) {
        this.uuidWithLogin = uuidWithLogin;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }
}
