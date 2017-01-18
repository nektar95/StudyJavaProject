package restaurant;

import java.io.Serializable;

/**
 * Created by Aleksander Kaźmierczak on 27.11.2016.
 */
public class Adress implements Serializable ,Comparable<Adress>{
    private int x;
    private int y;

    public Adress(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int compareTo(Adress o) {
        if(x == o.getX() && y == o.getY()){
            return 0;
        }
        return -1;
    }
}
