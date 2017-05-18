package Saboteur;


import Board.Couple;
import Cards.ActionCard;
import Cards.RepareSabotageCard;

import Player.Player;

import java.util.ArrayList;

public class Saboteur {

    Moteur m;

    public Saboteur(){

    }
    
    public void start_game(){


        System.out.println(m);

        try {
            System.out.println(m);
            while(!m.allRoleAreSet()){
                m.chooseRole();
            }

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

    }

    public Moteur getMoteur(){
        return m;
    }


    // init du moteur a partir d'un tableau de joueur
    public void initMoteur(ArrayList<Player> arrayPlayer){
        m = new Moteur(arrayPlayer);
    }




}

