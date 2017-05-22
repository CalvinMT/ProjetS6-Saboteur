package Board;

public class Couple {
    private int l, c;


    public Couple() {
        this.l = 0;
        this.c = 0;
    }

    public Couple(int l, int c) {
        this.l = l;
        this.c = c;
    }

    public int getLine() {
        return l;
    }

    public int getColumn() {
        return c;
    }

    public void setLine(int l) {
        this.l = l;
    }

    public void setColumn(int c) {
        this.c = c;
    }

    /*    public boolean equals(Couple c) {
        return this.x == c.getLine() && this.y == c.getColumn();
    }*/

    @Override
    public String toString() {
        return "("+this.l+","+this.c+")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Couple)) return false;

        Couple couple = (Couple) o;

        if (getLine() != couple.getLine()) return false;
        return getColumn() == couple.getColumn();
    }

    @Override
    public int hashCode() {
        int result = getLine();
        result = 31 * result + getColumn();
        return result;
    }
}
