package Board;

import Cards.GalleryCard;

public class Node {
    private static final int NONE = -1;

    public GalleryCard card;
    private int north;
    private int south;
    private int east;
    private int west;

    private int pathRes;
    private int pathLength;

    Node(){
        this(new GalleryCard(), NONE, NONE, NONE, NONE);
    }
    Node(GalleryCard c){
        this(c, NONE, NONE, NONE, NONE);
    }

    Node(GalleryCard c, int n, int s, int e, int w) {
        this(c, n, s, e, w, c.getResist(), 1);
    }

    Node(GalleryCard c, int n, int s, int e, int w, int r, int l) {
            this.card = c;
            this.north = n;
            this.south = s;
            this.east = e;
            this.west = w;
            this.pathRes = r;
            this.pathLength = l;
    }

    public void setNorth(int north) {
        this.north = north;
    }

    public void setSouth(int south) {
        this.south = south;
    }

    public void setEast(int east) {
        this.east = east;
    }

    public void setWest(int west) {
        this.west = west;
    }

    public void setPathRes(int pathRes) {
        this.pathRes = pathRes;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public boolean reached(){
        return (north != -1 || south != -1 || east != -1 || west != -1);
    }

    public int getNorth() {
        return north;
    }

    public int getSouth() {
        return south;
    }

    public int getEast() {
        return east;
    }

    public int getWest() {
        return west;
    }

    public GalleryCard getCard(){
        return this.card;
    }

    @Override
    public String toString() {
        return "Card :\n" + this.card.simplified() + "\n" + "Indexes : \nNorth : " + this.getNorth() + "\nSouth : " + this.getSouth() + "\nEast : " + this.getEast() + "\nWest : " + this.getWest() + "\n";

    }

    public int getPathRes() {
        return pathRes;
    }

    public int getPathLength() {
        return pathLength;
    }

    public boolean equals(Node n) {
        return this.card.equals(n.card) &&
        this.north == n.getNorth() &&
        this.south == n.getSouth() &&
        this.east == n.getEast() &&
        this.west == n.getWest();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (NONE != node.NONE) return false;
        if (getNorth() != node.getNorth()) return false;
        if (getSouth() != node.getSouth()) return false;
        if (getEast() != node.getEast()) return false;
        if (getWest() != node.getWest()) return false;
        return card != null ? card.equals(node.card) : node.card == null;
    }

    @Override
    public int hashCode() {
        int result = NONE;
        result = 31 * result + (card != null ? card.hashCode() : 0);
        result = 31 * result + getNorth();
        result = 31 * result + getSouth();
        result = 31 * result + getEast();
        result = 31 * result + getWest();
        return result;
    }

}
