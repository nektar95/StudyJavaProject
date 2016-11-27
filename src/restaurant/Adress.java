package restaurant;

import java.io.Serializable;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Adress implements Serializable {
    private int x;
    private int y;

    public Adress(int x, int y) {
        this.x = x;
        this.y = y;
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
}
