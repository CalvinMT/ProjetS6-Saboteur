package Saboteur;


import Board.Couple;
import Cards.ActionCard;
import Cards.RepareSabotageCard;

import Player.Player;

import java.util.ArrayList;

public class Saboteur {

    static public Moteur engine;

    static public Moteur getMoteur(){
        return engine;
    }

    static public void setMoteur(Moteur m){
        engine = m;
    }


    // init du moteur a partir d'un tableau de joueur
    static public void initMoteur(ArrayList<Player> arrayPlayer){
//        engine = new Moteur(arrayPlayer, "--debugBoard");
        engine = new Moteur(arrayPlayer, "");
    }


    static public boolean containsOption(String option, String [] args){
        for(int i=0; i<args.length; i++){
            if(args[i].equals(option)){
                return true;
            }
        }
        return false;
    }


}

