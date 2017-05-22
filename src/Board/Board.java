package Board;


import Cards.GalleryCard;
import Cards.GoalCard;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Random;

public class Board {
    private ArrayList<Node> mine = new ArrayList<Node>();
    private Hashtable<Couple, Node> accessCard = new Hashtable<Couple, Node>();
    private ArrayList<Couple> positionCandidates
            = new ArrayList<Couple>();

    public Board() {
        Random r = new Random();
        int gold = r.nextInt(3) -1;
        int x;

        mine.add(new Node());

        for (int i = -1; i < 2; i++) {
            x = 2*i;
            if (i == gold) {
                mine.add(new Node(new GoalCard(new Couple(x, 8), true, true, true ,true, true))); // Minerai
            }
            else {
                if (r.nextInt(2) == 1) {
                    mine.add(new Node(new GoalCard(new Couple(x, 8), false, false, true, true, false))); // Sans minerai droit
                }
                else {
                    mine.add(new Node(new GoalCard(new Couple(x, 8), false, false, true, false, false))); // Sans minerai gauche
                }
            }
        }

    }

    // Debug
    public Board(GalleryCard start, GoalCard but1, GoalCard but2, GoalCard but3) {
        mine.add(new Node(start));
        mine.add(new Node(but1));
        mine.add(new Node(but2));
        mine.add(new Node(but3));
    }


    // Action on the board
    public void addCard(GalleryCard card) {
        Node n = new Node(card);

        for (int i = 0; i < mine.size(); i++) {
            if ((mine.get(i).card.getLine() == (card.getLine() - 1)) && (mine.get(i).card.getColumn() == card.getColumn())) {
                n.setNorth(i);
                mine.get(i).setSouth(mine.size());
            }
            else if ((mine.get(i).card.getLine() == (card.getLine() + 1)) && (mine.get(i).card.getColumn() == card.getColumn())) {
                n.setSouth(i);
                mine.get(i).setNorth(mine.size());
            }
            else if ((mine.get(i).card.getLine() == card.getLine()) && (mine.get(i).card.getColumn() == (card.getColumn() + 1))) {
                n.setEast(i);
                mine.get(i).setWest(mine.size());
            }
            else if ((mine.get(i).card.getLine() == (card.getLine())) && (mine.get(i).card.getColumn() == (card.getColumn() - 1))) {
                n.setWest(i);
                mine.get(i).setEast(mine.size());
            }
        }

        mine.add(n);
    }

    public void removeCard(Couple coord) {
        int idx = 0;
        for (int i = 0; i < mine.size(); i++) {
            if (mine.get(i).card.getLine() == coord.getLine() && mine.get(i).card.getColumn() == coord.getColumn()) {
                idx = i;
                mine.remove(i);
            }
        }

        for (int i = 0; i < mine.size(); i++) {
            if(mine.get(i).getNorth() == idx) {
                mine.get(i).setNorth(-1);
            }
            else if(mine.get(i).getNorth() > idx) {
                mine.get(i).setNorth(mine.get(i).getNorth() - 1);
            }

            if(mine.get(i).getSouth() == idx) {
                mine.get(i).setSouth(-1);
            }
            else if(mine.get(i).getNorth() > idx) {
                mine.get(i).setSouth(mine.get(i).getSouth() - 1);
            }

            if(mine.get(i).getEast() == idx) {
                mine.get(i).setEast(-1);
            }
            else if(mine.get(i).getEast() > idx) {
                mine.get(i).setEast(mine.get(i).getEast() - 1);
            }

            if(mine.get(i).getWest() == idx) {
                mine.get(i).setWest(-1);
            }
            else if(mine.get(i).getWest() > idx) {
                mine.get(i).setWest(mine.get(i).getWest() - 1);
            }
        }
    }

    public void putCard(GalleryCard c, int line, int column){
        Couple cou = new Couple(line, column);

        c.setLine(line);
        c.setColumn(column);

        addCard(c);

        computeAccessCards();
    }


    // Computations
    public void computeAccessCards() {
        LinkedList<Node> queue = new LinkedList<Node>(),
                visited = new LinkedList<Node>();

        Node currentNode, newNode;
        Couple cpl;

        try {
            queue.add(mine.get(0));
            while (!queue.isEmpty()) { // Tant qu'il y a des cartes à parcourir
                // System.out.println(mine);

                currentNode = queue.remove(); // On défile
                visited.add(currentNode); // On ajoute la carte actuelle aux cartes visitées
                accessCard.put(new Couple(currentNode.card.getLine(), currentNode.card.getColumn()), currentNode); // Et aux cartes accessibles

                if (currentNode.card.canHasNorth()) {
                    if (currentNode.getNorth() != -1) { // Si il y a une carte au nord
                        newNode = mine.get(currentNode.getNorth());
                        if (!visited.contains(newNode)) { // Si la carte au nord n'a pas été visitée
                            queue.add(newNode); // On l'ajoute dans la queue
                        }
                    }
                    else { // Si il n'y a pas de carte au nord (case vide)
                        cpl = new Couple(currentNode.card.getLine() - 1, currentNode.card.getColumn());
                        if (!positionCandidates.contains(cpl)) { // Si la position n'a pas déjà été ajoutée
                            positionCandidates.add(cpl); // On l'ajoute aux possibilités
                        }
                    }
                }
                if (currentNode.card.canHasSouth()) {
                    if (currentNode.getSouth() != -1) { // Si il y a une carte au sud
                        newNode = mine.get(currentNode.getSouth());
                        if (!visited.contains(newNode)) { // Si la carte au sud n'a pas été visitée
                            queue.add(newNode); // On l'ajoute dans la queue
                        }
                    }
                    else { // Si il n'y a pas de carte au sud (case vide)
                        cpl = new Couple(currentNode.card.getLine() + 1, currentNode.card.getColumn());
                        if (!positionCandidates.contains(cpl)) { // Si la position n'a pas déjà été ajoutée
                            positionCandidates.add(cpl); // On l'ajoute aux possibilités
                        }
                    }
                }
                if (currentNode.card.canHasEast()) {
                    if (currentNode.getEast() != -1) { // Si il y a une carte à l'est
                        newNode = mine.get(currentNode.getEast());
                        if (!visited.contains(newNode)) { // Si la à l'est carte n'a pas été visitée
                            queue.add(newNode);
                        }
                    }
                    else {
                        cpl = new Couple(currentNode.card.getLine(), currentNode.card.getColumn() + 1);
                        if (!positionCandidates.contains(cpl)) {
                            positionCandidates.add(cpl);
                        }
                    }
                }
                if (currentNode.card.canHasWest()) {
                    if (currentNode.getWest() != -1) { // Si il y a une carte à l'ouest
                        newNode = mine.get(currentNode.getWest());
                        if (!visited.contains(newNode)) { // Si la carte n'a pas été visitée
                            queue.add(newNode);
                        }
                    }
                    else {
                        cpl = new Couple(currentNode.card.getLine(), currentNode.card.getColumn() - 1);
                        if (!positionCandidates.contains(cpl)) {
                            positionCandidates.add(cpl);
                        }
                    }
                }
            }
        }
        catch (IllegalStateException e) {
            System.err.println("No more place in queue");
            System.err.println(e.getMessage() + " due to " + e.getCause());
        }
    }

    // TODO : Tests
    public boolean isCompatibleWithNeighbors(GalleryCard c, Couple currPos) {
        Node currNode;
        currNode = getNodeFromMine(new Couple(currPos.getLine() - 1, currPos.getColumn()));
        if (currNode != null) {
            if ((c.canHasNorth() && !currNode.card.canHasSouth()) ||  (!c.canHasNorth() && currNode.card.canHasSouth())){
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getLine() + 1, currPos.getColumn()));
        if (currNode != null) {
            if ((c.canHasSouth() && !currNode.card.canHasNorth()) || (!c.canHasSouth() && currNode.card.canHasNorth())) {
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getLine(), currPos.getColumn() + 1));
        if (currNode != null) {
            if ((c.canHasEast() && !currNode.card.canHasWest()) || (!c.canHasEast() && currNode.card.canHasWest())) {
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getLine(), currPos.getColumn() - 1));
        if (currNode != null) {
            if ((c.canHasWest() && !currNode.card.canHasEast()) || (!c.canHasWest() && currNode.card.canHasEast())) {
                return false;
            }
        }

        return true;
    }

    public void computePossiblePositions(GalleryCard c, ArrayList<Couple> possiblePositions) {

        this.computeAccessCards();
        for (int i = 0; i < possiblePositions.size(); i++) {
            if (!isCompatibleWithNeighbors(c, possiblePositions.get(i)) && !isCompatibleWithNeighbors(c.rotate(), possiblePositions.get(i))) {
                possiblePositions.remove(i);
                i--;
            }
        }
    }


    // Getters
    public ArrayList<Couple> getPossiblePositions(GalleryCard c) {
        ArrayList<Couple> possiblePositions = positionCandidates;
        computePossiblePositions(c, possiblePositions);
        return possiblePositions;
    }

    public ArrayList<Node> getMine() {
        return mine;
    }

    public Hashtable<Couple, Node> getAccessCard() {
        return accessCard;
    }

    public Node getNodeFromMine(Couple c) {
        int i = 1;
        Node n = getMineElement(0);

        while ((i < getMineSize()) && !c.equals(new Couple(n.card.getLine(), n.card.getColumn()))) {
            n = getMineElement(i);
            i++;
        }

        if (!c.equals(new Couple(n.card.getLine(), n.card.getColumn()))) {
            n = null;
        }
        return n;
    }

    // Debug: Les fonctions ci-après sont prévues pour les uniquement, aucune verification n'est effectuée

    public Node getMineElement(int i) {
        return mine.get(i);
    }

    public Node getAccessCardElement(String k) {
        return accessCard.get(k);
    }

    public int getMineSize() {
        return mine.size();
    }
}
