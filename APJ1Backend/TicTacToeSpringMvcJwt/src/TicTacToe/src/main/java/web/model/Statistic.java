package web.model;

public class Statistic {

    private int uuid;

    private double ratio;

    public Statistic(int uuid, Double ratio) {
        this.uuid = uuid;
        if (ratio == null) {
            this.ratio = 0;
        }
        else
        {
            this.ratio = ratio;
        }
    }

    public int getUuid() {
        return this.uuid;
    }
    public double getRatio() {
        return this.ratio;
    }
    public void setUuid(int uuid){
        this.uuid = uuid;
    }
    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
