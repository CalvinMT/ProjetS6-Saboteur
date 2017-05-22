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
            if ((mine.get(i).card.getX() == (card.getX() - 1)) && (mine.get(i).card.getY() == card.getY())) {
                n.setNorth(i);
                mine.get(i).setSouth(mine.size());
            }
            else if ((mine.get(i).card.getX() == (card.getX() + 1)) && (mine.get(i).card.getY() == card.getY())) {
                n.setSouth(i);
                mine.get(i).setNorth(mine.size());
            }
            else if ((mine.get(i).card.getX() == card.getX()) && (mine.get(i).card.getY() == (card.getY() + 1))) {
                n.setEast(i);
                mine.get(i).setWest(mine.size());
            }
            else if ((mine.get(i).card.getX() == (card.getX())) && (mine.get(i).card.getY() == (card.getY() - 1))) {
                n.setWest(i);
                mine.get(i).setEast(mine.size());
            }
        }

        mine.add(n);
    }

    public void removeCard(Couple coord) {
        int idx = 0;
        for (int i = 0; i < mine.size(); i++) {
            if (mine.get(i).card.getX() == coord.getX() && mine.get(i).card.getY() == coord.getY()) {
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


    // Path Resistance
    /*public boolean isNeighbourVisited(int idx, LinkedList<Node> v) {
        if (idx !=-1) return v.contains(mine.get(idx));
        return true;
    }

    public boolean allNeighborsVisited(Node n, LinkedList<Node> v) {
        boolean b = isNeighbourVisited(n.getNorth(), v)
                && isNeighbourVisited(n.getSouth(), v)
                && isNeighbourVisited(n.getEast(), v)
                && isNeighbourVisited(n.getWest(), v);
        System.out.printf("\t%-5s -> ", b);
        System.out.println(n);
        return b;
    } */

    // Computations




    private int getRes(int idx, Node end, LinkedList<Node> path){
        Node curr;
        if (idx != -1) {
            curr = mine.get(idx);
            if (!path.contains(curr)) {
                System.out.printf("getRes[%2d] %s\n\t start : {(%2d,%2d) %5s/%2d}\n\t end   : {(%2d,%2d) %5s/%2d}\n", curr.getPathRes(), path, curr.card.getX(), curr.card.getY(), curr.card.getConfig(), curr.getPathRes(), end.card.getX(), end.card.getY(), end.card.getConfig(), end.getPathRes());
                return pathRes(curr, end, path);
            }
        }
        System.out.println("END getRes");
        return 0;
    }

    public int pathRes(Node start, Node end, LinkedList<Node> path) {
        if (start == end) {
            System.out.printf("pathRes[%2d] %s\n\t start : {(%2d,%2d) %5s/%2d}\n\t end   : {(%2d,%2d) %5s/%2d}\nEND pathRes\n", start.getPathRes(), path, start.card.getX(), start.card.getY(), start.card.getConfig(), start.getPathRes(), end.card.getX(), end.card.getY(), end.card.getConfig(), end.getPathRes());
            return end.getPathRes();
        }
        else {
            path.contains(start);
            return Math.max(getRes(start.getNorth(), end, path), Math.max(getRes(start.getSouth(), end, path), Math.max(getRes(start.getEast(), end, path), getRes(start.getWest(), end, path)))) + start.card.getResist();
        }
    }






    // Compute pathResistance
    public void computePathRes_rec(Node start, Node end, LinkedList<Node> path, int res) {
        Node curr;
        //System.out.printf("[%2d] %s\n\t start : {(%2d,%2d) %5s/%2d}\n\t end   : {(%2d,%2d) %5s/%2d}\n", res, path, start.card.getX(), start.card.getY(), start.card.getConfig(), start.getPathRes(), end.card.getX(), end.card.getY(), end.card.getConfig(), end.getPathRes());

        if (start == end) {
            //System.out.println("END");
            res = res + end.card.getResist();
            if (res > end.getPathRes()) end.setPathRes(res);
        }
        else {
            res += start.card.getResist();

            if (res > start.getPathRes()) start.setPathRes(res); // Garde la resistance max

            if (start.getNorth() != -1) { // Si il y a un élément au nord
                curr = mine.get(start.getNorth());
                if (!path.contains(curr)) {
                //    System.out.println("\t\t\tNorth add : " + curr);
                    path.add(curr);
                    computePathRes_rec(curr, end, path, res); // Si l'élément nord n'a pas déjà été visité
                }
                //else System.out.println("\t\tNorth path contains " + curr);
            }
            if (start.getSouth() != -1) { // Si il y a un élément au sud

                curr = mine.get(start.getSouth());
                if (!path.contains(curr)) {
                //    System.out.println("\t\t\tSouth add : " + curr);
                    path.add(curr);
                    computePathRes_rec(curr, end, path, res); // Si l'élément sud n'a pas déjà été visité
                }
                //else System.out.println("\t\tSouth path contains " + curr);
            }
            if (start.getEast() != -1) { // Si il y a un élément à l'est

                curr = mine.get(start.getEast());
                if (!path.contains(curr)) {
                //    System.out.println("\t\t\tEast  add : " + curr);
                    path.add(curr);
                    computePathRes_rec(curr, end, path, res); // Si l'élément à l'est n'a pas déjà été visité
                }
                //else System.out.println("\t\tEast  path contains " + curr);
            }
            if (start.getWest() != -1) { // Si il y a un élément à l'ouest
                curr = mine.get(start.getWest());
                if (!path.contains(curr)) {
                //    System.out.println("\t\t\tWest  add : " + curr);
                    path.add(curr);
                    computePathRes_rec(curr, end, path, res); // Si l'élément ouest n'a pas déjà été visité
                }
                //else System.out.println("\t\tWest  path contains " + curr);
            }
        }
    }

    public void computePathRes() {
        Node start, n;
        LinkedList<Node> path = new LinkedList<Node>();
        start = mine.get(0);
        path.add(start);
        for (int i = 4; i < mine.size(); i++) {
            n = mine.get(i);
            computePathRes_rec(start, n, path, 0);
        }
    }

    public void computeAccessCards() {
        LinkedList<Node> queue = new LinkedList<Node>(),
                visited = new LinkedList<Node>();

        Node currentNode, newNode;
        Couple cpl;
        int res;

        try {
            queue.add(mine.get(0));
            while (!queue.isEmpty()) { // Tant qu'il y a des cartes à parcourir

                // TODO : Migrer les calculs de résistance dans une autre fonction, recurssive,
                // TODO | gardant en mémoire les chemins (différent de garder les cartes visitées)

                // TODO : FACTORISER!!! - G. Huard 2017
                currentNode = queue.remove(); // On défile
                visited.add(currentNode); // On ajoute la carte actuelle aux cartes visitées
                accessCard.put(new Couple(currentNode.card.getX(), currentNode.card.getY()), currentNode); // Et aux cartes accessibles
                if (currentNode.card.canHasNorth()) {
                    if (currentNode.getNorth() != -1) { // Si il y a une carte au nord
                        newNode = mine.get(currentNode.getNorth());
                        if (!visited.contains(newNode)) { // Si la carte au nord n'a pas été visitée
                            queue.add(newNode); // On l'ajoute dans la queue
                        }
                    }
                    else { // Si il n'y a pas de carte au nord (case vide)
                        cpl = new Couple(currentNode.card.getX() - 1, currentNode.card.getY());
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
                        cpl = new Couple(currentNode.card.getX() + 1, currentNode.card.getY());
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
                        cpl = new Couple(currentNode.card.getX(), currentNode.card.getY() + 1);
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
                        cpl = new Couple(currentNode.card.getX(), currentNode.card.getY() - 1);
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

    public boolean isCompatibleWithNeighbors(GalleryCard c, Couple currPos) {
        Node currNode;
        currNode = getNodeFromMine(new Couple(currPos.getX() - 1, currPos.getY()));
        if (currNode != null) {
            if ((c.canHasNorth() && !currNode.card.canHasSouth()) ||  (!c.canHasNorth() && currNode.card.canHasSouth())){
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getX() + 1, currPos.getY()));
        if (currNode != null) {
            if ((c.canHasSouth() && !currNode.card.canHasNorth()) || (!c.canHasSouth() && currNode.card.canHasNorth())) {
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getX(), currPos.getY() + 1));
        if (currNode != null) {
            if ((c.canHasEast() && !currNode.card.canHasWest()) || (!c.canHasEast() && currNode.card.canHasWest())) {
                return false;
            }
        }
        currNode = getNodeFromMine(new Couple(currPos.getX(), currPos.getY() - 1));
        if (currNode != null) {
            if ((c.canHasWest() && !currNode.card.canHasEast()) || (!c.canHasWest() && currNode.card.canHasEast())) {
                return false;
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

        while ((i < getMineSize()) && !c.equals(new Couple(n.card.getX(), n.card.getY()))) {
            n = getMineElement(i);
            i++;
        }

        if (!c.equals(new Couple(n.card.getX(), n.card.getY()))) {
            n = null;
        }
        return n;
    }

    // Debug: Les fonctions ci-après sont prévues pour les tests uniquement, aucune verification n'est effectuée

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
