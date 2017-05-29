package Saboteur;


import Cards.GalleryCard;
import Player.Player;
import Player.IA;
import Player.PlayerHuman;
import Player.Player.Difficulty;
import Saboteur.Moteur.State;

import java.util.ArrayList;

public class Saboteur {

    static public Moteur engine;

    static public int manche = 0;

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
        manche = 0;
    }

    static public void resetMoteur(ArrayList<Player> arrayPlayer){
//        engine = new Moteur(arrayPlayer, "--debugBoard");
        engine = new Moteur(arrayPlayer, "");
        manche++;
    }



    // game de test 1
    static public void game1(){

        ArrayList<Player> arrayPlayer = new ArrayList<Player>();
        arrayPlayer.add(new PlayerHuman(0, "Joueur 1", "avatar_anonyme"));
        arrayPlayer.add(new PlayerHuman(1, "DrZed", "avatar_anonyme"));
        arrayPlayer.add(new PlayerHuman(2, "Ekalkas", "avatar_anonyme"));
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

        engine.setCurrentPlayer(2);

//        engine.getAllPlayers().get(2).setSabotage(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon));

        GalleryCard card1 = new GalleryCard(GalleryCard.Gallery_t.tunnel, true, true, true, true, true);
        GalleryCard card2 = new GalleryCard(GalleryCard.Gallery_t.tunnel, true, false, false, true, true);

        engine.getBoard().putCard(card1, 0, 1);
        engine.getBoard().putCard(card2, 0, 2);

        engine.setState(State.Game);
//        engine.getBoard().computeAccessCards();


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

