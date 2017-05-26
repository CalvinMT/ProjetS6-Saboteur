package Saboteur;


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


    static public void main(String [] args){

        Moteur m = new Moteur("loadfile");

        System.out.println(m);




    }


}
