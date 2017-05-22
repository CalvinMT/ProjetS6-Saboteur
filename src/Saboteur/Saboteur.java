package Saboteur;


import Board.Couple;
import Cards.ActionCard;
import Cards.RepareSabotageCard;

import Player.Player;

import java.util.ArrayList;

public class Saboteur {

    static public Moteur engine;

    /*public Saboteur(){

    }
    
    public void start_game(){


        System.out.println(m);

        try {
            System.out.println(m);


        } catch(Exception e){
            System.err.println("Erreur choix du role");
        }

        m.promptPlayers();

        System.out.println("Maj");
        System.out.println();

        m.play(new ActionCard("Map"), new Couple(0, 8));

        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Lantern), RepareSabotageCard.Tools.Lantern, m.getPlayer(2));

        m.promptPlayers();

    }*/

    static public Moteur getMoteur(){
        return engine;
    }


    // init du moteur a partir d'un tableau de joueur
    static public void initMoteur(ArrayList<Player> arrayPlayer){
        engine = new Moteur(arrayPlayer);
    }




}

