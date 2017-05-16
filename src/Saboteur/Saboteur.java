package Saboteur;


import Board.Couple;
import Cards.ActionCard;
import Cards.RepareSabotageCard;
<<<<<<< HEAD

public class Saboteur {

    static public void main(String [] args){

        int nbPlayer = 4;
        Moteur m = new Moteur(nbPlayer);
=======
import Player.Player;

import java.util.ArrayList;

public class Saboteur {

    static Moteur m;

    static public void main(String [] args){

        int nbPlayer = 4;
>>>>>>> 2123c36dd9bae55dc545d51e6d6029f2c6ad23a4

        try {
            System.out.println(m);
            while(!m.allRoleAreSet()){
                m.chooseRole(0);
            }

        } catch(Exception e){
            System.err.println("Erreur choix du role");
        }

        m.initHand();
        m.promptPlayers();

        System.out.println("Maj");
        System.out.println();

        m.play(new ActionCard("Map"), new Couple(0, 8));

        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Lantern), RepareSabotageCard.Tools.Lantern, m.getPlayer(2));

        m.promptPlayers();

    }

<<<<<<< HEAD
=======
    // init du moteur a partir d'un tableau de joueur
    static public void initMoteur(ArrayList<Player> arrayPlayer){
        m = new Moteur(arrayPlayer);
    }

>>>>>>> 2123c36dd9bae55dc545d51e6d6029f2c6ad23a4


}

