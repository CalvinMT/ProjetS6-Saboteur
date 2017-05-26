package Board;


import Cards.GalleryCard;
import Cards.GoalCard;

import java.util.*;

public class Board {
    private ArrayList<Node> mine = new ArrayList<Node>();
    private Hashtable<Couple, Node> accessCard = new Hashtable<Couple, Node>();
    private ArrayList<Couple> positionCandidates
            = new ArrayList<Couple>();

    public Board() {
        Random r = new Random();
        int gold = r.nextInt(3) -1;
        int x;

        ArrayList<Integer> tab =  new ArrayList<>();
        tab.add(-2);
        tab.add(0);
        tab.add(2);

        // start
        mine.add(new Node());


        x = tab.remove(r.nextInt(tab.size()));
        mine.add(new Node(new GoalCard(new Couple(x, 8), true, true, true ,true, true)));
        x = tab.remove(r.nextInt(tab.size()));
        mine.add(new Node(new GoalCard(new Couple(x, 8), false, true, true, false, false)));
        x = tab.remove(r.nextInt(tab.size()));
        mine.add(new Node(new GoalCard(new Couple(x, 8), false, true, false, true, false)));

        computeAccessCards();
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

    public void putCard(GalleryCard c, int line, int column){
        Couple cou = new Couple(line, column);

        c.setLine(line);
        c.setColumn(column);

        addCard(c);

        computeAccessCards();
    }

    public void removeCard(Couple coord) {
        int idx = 0;
        // suppression dans la mine
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
            else if(mine.get(i).getSouth() > idx) {
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

        computeAccessCards();
    }
    
    // Compute pathResistance
    public void computePathRes(Node start, LinkedList<Node> path, int res) {
        Node curr;

        path.add(start);

        res += start.card.getResist();

        if (res > start.getPathRes()) {
            start.setPathRes(res); // Garde la resistance max
            start.setPathLength(path.size());
        }

        if (start.getNorth() != -1) { // Si il y a un élément au nord
            curr = mine.get(start.getNorth());
            if (!path.contains(curr)) {
                computePathRes(curr, path, res); // Si l'élément nord n'a pas déjà été visité
            }
        }
        if (start.getSouth() != -1) { // Si il y a un élément au sud

            curr = mine.get(start.getSouth());
            if (!path.contains(curr)) {
                computePathRes(curr, path, res); // Si l'élément sud n'a pas déjà été visité
            }
        }
        if (start.getEast() != -1) { // Si il y a un élément à l'est
            curr = mine.get(start.getEast());
            if (!path.contains(curr)) {
                computePathRes(curr, path, res); // Si l'élément à l'est n'a pas déjà été visité
            }
        }
        if (start.getWest() != -1) { // Si il y a un élément à l'ouest
            curr = mine.get(start.getWest());
            if (!path.contains(curr)) {
                computePathRes(curr, path, res); // Si l'élément ouest n'a pas déjà été visité
            }
        }
    }


    public void computeAccessCards() {
        LinkedList<Node> queue = new LinkedList<Node>(),
                visited = new LinkedList<Node>();

        Node currentNode, newNode;
        Couple cpl;

        this.positionCandidates = new ArrayList<>();
        try {
            queue.add(mine.get(0));
            while (!queue.isEmpty()) { // Tant qu'il y a des cartes à parcourir

                // TODO : FACTORISER!!! - G. Huard 2017
                currentNode = queue.remove(); // On défile
                visited.add(currentNode); // On ajoute la carte actuelle aux cartes visitées

                if (currentNode.card.canHasCenter()) {
                    
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
                            if (!visited.contains(newNode)) { // Si la carte à l'est carte n'a pas été visitée
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
        }
        catch (IllegalStateException e) {
            System.err.println("No more place in queue");
            System.err.println(e.getMessage() + " due to " + e.getCause());
        }
    }

    //TODO Factoriser

    public boolean isCompatibleWithNeighbors(GalleryCard c, Couple currPos) {

        Node currNode;

        // nord
        currNode = getNodeFromMine(new Couple(currPos.getLine() - 1, currPos.getColumn()));
        if (currNode != null) {
            if ((c.canHasNorth() && !currNode.card.canHasSouth()) || (!c.canHasNorth() && currNode.card.canHasSouth())) {

                // si c'est un carte but non fixe
                if (currNode.getCard().isGoal() && !currNode.reached()) {
                    GalleryCard card = currNode.getCard().rotate();
                    if ((c.canHasNorth() && !card.canHasSouth()) || (!c.canHasNorth() && card.canHasSouth())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        // sud
        currNode = getNodeFromMine(new Couple(currPos.getLine() + 1, currPos.getColumn()));
        if (currNode != null) {
            if ((c.canHasSouth() && !currNode.card.canHasNorth()) || (!c.canHasSouth() && currNode.card.canHasNorth())) {

                // si c'est un carte but non fixe
                if (currNode.getCard().isGoal() && !currNode.reached()) {
                    GalleryCard card = currNode.getCard().rotate();
                    if ((c.canHasSouth() && !card.canHasNorth()) || (!c.canHasSouth() && card.canHasNorth())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        //est
        currNode = getNodeFromMine(new Couple(currPos.getLine(), currPos.getColumn()+1) );
        if (currNode != null) {
            if ((c.canHasEast() && !currNode.card.canHasWest()) || (!c.canHasEast() && currNode.card.canHasWest())) {

                // si c'est un carte but non fixe
                if (currNode.getCard().isGoal() && !currNode.reached()) {
                    GalleryCard card = currNode.getCard().rotate();
                    if ((c.canHasEast() && !card.canHasWest()) || (!c.canHasEast() && card.canHasWest())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        //ouest

        currNode = getNodeFromMine(new Couple(currPos.getLine(), currPos.getColumn()-1 ));
        if (currNode != null) {
            if ((c.canHasWest() && !currNode.card.canHasEast()) || (!c.canHasWest() && currNode.card.canHasEast())) {

                // si c'est un carte but non fixe
                if (currNode.getCard().isGoal() && !currNode.reached()) {
                    GalleryCard card = currNode.getCard().rotate();
                    if ((c.canHasWest() && !card.canHasEast()) || (!c.canHasWest() && card.canHasEast())) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void computePossiblePositions(GalleryCard c, ArrayList<Couple> possiblePositions) {

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

    public boolean goldReached(){

        final Node goal1 = mine.get(1);
        final Node goal2 = mine.get(2);
        final Node goal3 = mine.get(3);

        return (goal1.reached() && goal1.getCard().isGold() || goal2.reached() && goal2.getCard().isGold() || goal3.reached() && goal3.getCard().isGold());
        //TODO à changer car la carte gold peut etre bloquer et inacessible
    }

    public String mine(){
        String renvoi = "Mine:\n";

        for(int i=0; i<mine.size(); i++){
            renvoi += "Index: "+i+"\n";
            renvoi +=  mine.get(i) + "\n";
        }


        return renvoi;
    }

    public String debugAccessible(){
        String renvoi = "Accessible: \n";
        computeAccessCards();
        Node node;

        Set<Couple> hash = accessCard.keySet();

        Iterator<Couple> it = hash.iterator();

        while(it.hasNext()){
            node = accessCard.get(it.next());
            renvoi += node + "\n";
        }

        return renvoi;
    }


    // Debug: Les fonctions ci-après sont prévues pour les uniquement, aucune verification n'est effectuée
    public Node getMineElement(int i) {
        return mine.get(i);
    }

    public Node getAccessCardElement(Couple k) {
        return accessCard.get(k);
    }

    public int getMineSize() {
        return mine.size();
    }

}
