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

/*    public boolean equals(Couple c) {
        return this.x == c.getX() && this.y == c.getY();
    }*/

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Couple)) return false;

        Couple couple = (Couple) o;

        if (getX() != couple.getX()) return false;
        return getY() == couple.getY();
    }

    @Override
    public int hashCode() {
        int result = getX();
        result = 31 * result + getY();
        return result;
    }
}
