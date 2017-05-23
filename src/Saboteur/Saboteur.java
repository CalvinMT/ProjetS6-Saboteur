package Saboteur;


import Board.*;
import Cards.ActionCard;
import Cards.RepareSabotageCard;
import Cards.RepareSabotageCard.Tools;
import Player.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import Cards.*;
public class Saboteur {

    static Moteur m;

    static public void main(String [] args){
    	
    	
    	    	
    	int nbPlayer = 4;
        Moteur m1 = new Moteur(nbPlayer);
        System.out.println("1er moteur au depart: \n" + m1);
        //System.out.println("Moteur m1 before roles are set:\n" + m1 + "\n");
        try {
        	while(!m1.allRoleAreSet()){
                m1.chooseRole(0);
            }
        }
        catch(Exception e){
        	System.out.println(e);
        }
        m1.initHand();
        System.out.println("\n1er Moteur après choix de roles : ");
        System.out.println(m1);
        m1.save("loadfile");
        Moteur m2 = new Moteur("loadfile");
        System.out.println("Moteur m2, saved version of Moteur m1 : \n" + m2);
        System.out.println("Moteur m1 Board : \n" + m1.board.getMine() + "\n");
        
        System.out.println("Moteur m2 Board : \n" + m2.board.getMine() + "\n");
        
        
        
       
       
        
        
        
 
/*
        m.initHand();
        m.promptPlayers();

        System.out.println("Maj");
        System.out.println();

        m.play(new ActionCard("Map"), new Couple(0, 8));

        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), m.getPlayer(2));
        m.play(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Lantern), RepareSabotageCard.Tools.Lantern, m.getPlayer(2));

        m.promptPlayers();
        
  */    
        
        
        

    }

    // init du moteur a partir d'un tableau de joueur
    static public void initMoteur(ArrayList<Player> arrayPlayer){
        m = new Moteur(arrayPlayer);
    }



}

