package Player;

import Board.Board;
import Cards.GalleryCard;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * Created by oloar on 26/05/2017.
 */
public class TreeNode {
    Board board;
    boolean isMax;
    ArrayList<TreeNode> next;

    public TreeNode() {
        this(new Board(), true, new ArrayList<>());
    }

    public TreeNode(Board board, boolean isMax) {
        this(board, isMax, new ArrayList<>());
    }
    public TreeNode(Board board, boolean isMax, ArrayList<TreeNode> next) {
        this.board = board;
        this.isMax = isMax;
        this.next = next;
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<TreeNode> getNext() {
        return next;
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
        return min(abs(this.board.getMineElement(1).card.getLine() - c.getLine()) + abs(this.board.getMineElement(1).card.getColumn() - c.getColumn()),
               min(abs(this.board.getMineElement(2).card.getLine() - c.getLine()) + abs(this.board.getMineElement(2).card.getColumn() - c.getColumn()),
                   abs(this.board.getMineElement(3).card.getLine() - c.getLine()) + abs(this.board.getMineElement(3).card.getColumn() - c.getColumn())));
    }

    public float evaluate() {
        int i;
        GalleryCard curr;
        float value = 0;
        for (i = 0; i < this.board.getMineSize(); i++) {
            curr = this.board.getMineElement(i).card;
            if (this.board.getAccessCardElement(curr.getCoord()).equals(curr)) // On ne considère que les cartes accessibles
                value += getMinDistanceToGoals(curr);
        }
        return value / i; // Distance moyenne au but
    }
}
