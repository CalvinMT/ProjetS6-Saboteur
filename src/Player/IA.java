package Player;

import Cards.HandPlayer;
import Cards.PlayerAttribute;

/**
 * Created by thespygeek on 11/05/17.
 */
public class IA extends Player{



    public IA(int index){
        this.playerName = "Joueur" + index;
        this.num = index;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public IA(int index, String name){
        this.playerName = name;
        this.num = index;
        this.difficulty = Difficulty.Easy;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
    }

    public IA(int index, String name, Difficulty d){
        this.playerName = name;
        this.num = index;
        this.difficulty = d;
        this.goldPoints = 0;
        attributeCards = new PlayerAttribute();
        this.playableCards = new HandPlayer();
        this.avatar = "robot_miner";
    }



    @Override
    public String toString(){
        String renvoi = "";

        renvoi += "Player: "+this.playerName + "\n";
        renvoi += "Type: IA\n";
        renvoi += "Difficulté: "+this.difficulty + "\n";
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
