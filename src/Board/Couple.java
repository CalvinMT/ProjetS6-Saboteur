package Board;

public class Couple {
    private int x, y;


    public Couple() {
        this.x = 0;
        this.y = 0;
    }

    public Couple(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }
}
