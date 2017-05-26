/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;
import Board.Board;
import Cards.HandPlayer;
import Cards.PlayerAttribute;

/**
 *
 * @author uwalakae
 */
public class PlayerHuman extends Player{


    // TODO Factoriser

    // Constructeur
    public PlayerHuman(int index) {
        this(index, "Joueur " + index, new Board(), "avater_anonyme.png");
    }

    public PlayerHuman(int index, String playerHuman) {
        this(index, playerHuman, new Board(), "avater_anonyme.png");
    }

    public PlayerHuman(int index, Board b) {
        this(index, "Joueur " + index, b, "avatar_anonyme.png");
    }

    public PlayerHuman(int index, String playerName, String avatar) {
        this(index, playerName, new Board(), avatar);
    }


    public PlayerHuman(int index, String playerName, Board b) {
        this(index, playerName, b, "avatar_anonyme.png");
    }


    public PlayerHuman(int index, String playerName, Board b, String avatar) {
        this.num = index;
        this.playerName = playerName;
        this.difficulty = Difficulty.Player;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
        this.avatar = avatar;
    }


    @Override
    public void setGoldPoints(int gp) {
    	if (gp >= 0)
    		this.goldPoints += gp;
    }




}
