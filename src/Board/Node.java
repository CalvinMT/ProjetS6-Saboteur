package Board;

import Cards.GalleryCard;

public class Node {
    final int NONE = -1;

    public GalleryCard card;
    private int north = NONE;
    private int south = NONE;
    private int east = NONE;
    private int west = NONE;

    Node(){
        this.card = new GalleryCard();
    }

    Node(GalleryCard c){
        this.card = c;
    }

/*    Node(GalleryCard.Gallery_t t, int n, int s, int e, int w){
        this.card = new GalleryCard(t);
        this.idx.add(n);
        this.idx.add(s);
        this.idx.add(e);
        this.idx.add(w);
    }
*/

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
        return "Card :\n" + this.card + "\n" + "Indexes : \nNorth : " + this.getNorth() + "\nSouth : " + this.getSouth() + "\nEast : " + this.getEast() + "\nWest : " + this.getWest() + "\n";


    }

    public boolean equals(Node n) {
        return this.card.equals(n.card) &&
        this.north == n.getNorth() &&
        this.south == n.getSouth() &&
        this.east == n.getEast() &&
        this.west == n.getWest();
    }
}



