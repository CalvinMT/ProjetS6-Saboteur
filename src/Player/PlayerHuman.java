/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;
import Cards.*;
import Board.Board;
/**
 *
 * @author uwalakae
 */
public class PlayerHuman extends Player{



    // Constructeur
    public PlayerHuman() {
        this.playerName = "Joueur";
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public PlayerHuman(Board b) {
        this.playerName = "Joueur";
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
    }

    public PlayerHuman(String playerName) {
        this.playerName = playerName;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }


    public PlayerHuman(String playerName, Board b) {
        this.playerName = playerName;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
    }

    public PlayerHuman(int i, Board b) {
        this.playerName = "Joueur " + i;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.board = b;
    }

    @Override
    public String toString(){
        String renvoi = "";

        renvoi += "Player: "+this.playerName + "\n";
        renvoi += "Type: Humain\n";
        if(this.role == null){
            renvoi += "Aucun role pour l'instant\n";
        } else {
            renvoi += this.role + "\n";
        }
        renvoi += "Nombre d'or: "+this.goldPoints + "\n";
        renvoi += this.attributeCards + "\n";
        renvoi += this.playableCards + "\n";

        return renvoi;
    }
    
    
    
}
