package Player;

import Board.Board;
import Cards.GalleryCard;
import Cards.GoalCard;
import Board.Couple;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Created by oloar on 26/05/2017.
 */

public class TreeNode {
    private IA ia;
    private boolean isMax;
    private ArrayList<TreeNode> next;
    private float value;
    private Move move;

    public TreeNode() {
        this(true, new ArrayList<>(), new IA());
    }

    public TreeNode(boolean isMax, IA ia) {
        this(isMax, new ArrayList<>(), ia);
    }

    public TreeNode(boolean isMax, ArrayList<TreeNode> next, IA ia) {
        this.isMax = isMax;
        this.next = next;
        this.ia = ia;
    }

    public Board getBoard() {
        return this.ia.board;
    }

    public ArrayList<TreeNode> getNext() {
        return next;
    }

    public Move getMove() {
        return move;
    }

    public float getValue() {
        return value;
    }

    public ArrayList<Couple> getGoalsToTest() {
        return ia.getGoalsToTest();
    }

    public ArrayList<Player> getPlayers() {
        return ia.getAllPlayers();
    }

    public void setGoalsToTest(ArrayList<Couple> goals){
        this.ia.setGoalsToTest(goals);
    }

    public void setPlayers(ArrayList<Player> players) {
        this.ia.setAllPlayers(players);
    }

    public void setBoard(Board board) {
        this.ia.board = board;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void removeFromNext(TreeNode t) {
        this.next.remove(t);
    }

    public void addToNext(TreeNode t) {
        this.next.add(t);
    }

    public boolean isLeaf() {
        return this.next.size() == 0;
    }

    public boolean isMaxNode() {
        return isMax;
    }

    public int getMinDistanceToGoals(GalleryCard c) {
        return min(abs(this.ia.board.getMineElement(1).card.getLine() - c.getLine()) + abs(this.ia.board.getMineElement(1).card.getColumn() - c.getColumn()),
               min(abs(this.ia.board.getMineElement(2).card.getLine() - c.getLine()) + abs(this.ia.board.getMineElement(2).card.getColumn() - c.getColumn()),
                   abs(this.ia.board.getMineElement(3).card.getLine() - c.getLine()) + abs(this.ia.board.getMineElement(3).card.getColumn() - c.getColumn())));
    }

    public float evaluate() {
        int i;
        GalleryCard curr;
        float value = 0;
        for (i = 1; i < this.ia.board.getMineSize(); i++) {
            curr = this.ia.board.getMineElement(i).card;
            if (this.ia.board.getAccessCardElement(curr.getCoord()).equals(curr)) // On ne considère que les cartes accessibles
                value += getMinDistanceToGoals(curr);
        }
        return value / i; // Distance moyenne au but
    }
}
