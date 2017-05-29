package Saboteur;


import Board.Couple;
import Cards.ActionCard;
import Cards.RepareSabotageCard;

import Player.Player;
import Player.IA;
import Player.PlayerHuman;
import Player.Player.Difficulty;
import Saboteur.Moteur.State;

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



    // game de test 1
    static public void game1(){

        ArrayList<Player> arrayPlayer = new ArrayList<Player>();
        arrayPlayer.add(new PlayerHuman(0, "Joueur 1", "avatar_anonyme.png"));
        arrayPlayer.add(new PlayerHuman(1, "DrZed", "avatar_anonyme.png"));
        arrayPlayer.add(new PlayerHuman(2, "Ekalkas", "avatar_anonyme.png"));
        arrayPlayer.add(new IA(3, "IA 1" ,Difficulty.Easy));
        arrayPlayer.add(new IA(4, "IA 2" ,Difficulty.Medium));

        engine = new Moteur(arrayPlayer, "");

        while(!engine.allRoleAreSet()){
            try {
                engine.chooseRole(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        engine.setState(State.Game);

        System.out.println(engine);
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
        game1();
    }


}

