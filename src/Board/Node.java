package Board;
import Cards.GalleryCard;

public class Node {
    private Node north, south, east, west;
    public GalleryCard card;

    Node() {
        card = new GalleryCard();
    }

    Node(GalleryCard card) {
        this.card = card;
    }

    public void setNorth(Node north) {
        this.north = north;
    }

    public void setSouth(Node south) {
        this.south = south;
    }

    public void setEast(Node east) {
        this.east = east;
    }

    public void setWest(Node west) {
        this.west = west;
    }

    public Node getNorth() {
        return north;
    }

    public Node getSouth() {
        return south;
    }

    public Node getEast() {
        return east;
    }

    public Node getWest() {
        return west;
    }
}
