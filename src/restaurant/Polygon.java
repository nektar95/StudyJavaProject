package restaurant;

/**
 * Created by Aleksander Kaźmierczak  on 16.01.2017.
 */
public class Polygon {
    private int type;
    private int x;
    private int y;

    public Polygon(int x, int y,int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getType() {
        return type;
    }

    public void setType(int mType) {
        this.type = mType;
    }
}
