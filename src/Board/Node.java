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

    public Node(GalleryCard c){
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
    
    // GoalCard-false!GalleryCard:{gallery,-2,8,true,false,false,true,true}!-1,-1,-1,-1?GoalCard-true:GalleryCard:{gallery,0,8,true,true,true,true,true}:-1,-1,-1,-1? 
    // 
    
    public String saveToFile() {
    	String me = "";
    	me += this.card;
    	me += ":";
    	me += this.getNorth() + "," + this.getEast() + "," + this.getWest() + "," + this.getSouth();
    	return me;
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



