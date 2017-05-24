/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;
import Cards.*;
import Board.Board;

import java.util.Random;

/**
 *
 * @author uwalakae
 */
public class PlayerHuman extends Player{


    // TODO Factoriser

    // Constructeur
    public PlayerHuman(int index) {
        this.playerName = "Joueur "+index;
        this.num = index;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public PlayerHuman(int index, Board b) {
        this.playerName = "Joueur "+index;
        this.num = index;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
    }

    public PlayerHuman(int index, String playerName, String avatar) {
        this.playerName = playerName;
        this.num = index;
        this.difficulty = Difficulty.Player;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = avatar;
    }


    public PlayerHuman(int index, String playerName, Board b) {
        this.playerName = playerName;
        this.num = index;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
    }

    
    @Override
    public void setGoldPoints(int gp) {
    	if (gp >= 0)
    		this.goldPoints += gp;
    }
    
    @Override
    public String toString(){
        String renvoi = "";

        renvoi += this.playerName + "\n";
        renvoi += "Humain\n";
        if(this.role == null){
            renvoi += "Aucun role pour l'instant\n";
        } else {
            renvoi += this.role + "\n";
        }
        renvoi += this.goldPoints + "\n";
        renvoi += this.attributeCards + "\n";
        renvoi += this.playableCards + "\n";

        return renvoi;
    }

    
}
