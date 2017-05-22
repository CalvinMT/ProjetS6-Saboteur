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
        System.out.println("Moteur m1 before roles are set:\n" + m1 + "\n");
        try {
        	while(!m1.allRoleAreSet()){
                m1.chooseRole(0);
            }
        }
        catch(Exception e){
        	System.out.println(e);
        }
        m1.initHand();
        m1.save("m11file");
        System.out.println("Moteur m1 saved to text file after roles are set:\n" + m1 + "\n");
        Moteur m2 = new Moteur("m11file");
        //PlayerHuman p = new PlayerHuman ("Emez");
        //System.out.println(p+"heehee");
        System.out.println();
        System.out.println("Moteur m2, saved version of Moteur m1:\n" + m2);
 /*        
        try {
        	// construct moteur
        	m = new Moteur(nbPlayer);
            System.out.println(m);
            while(!m.allRoleAreSet()){
                m.chooseRole(0);
            }
            
            m.save("testfile");
            
            Moteur savedMoteur = new Moteur("moteurTest");
            System.out.println("Loaded : \n" + savedMoteur);
			
        } catch(Exception e){
        	System.err.println(e);
            System.err.println("Erreur choix du role");
        }
        */
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
        
        
        /*
        m.save("testfile1");
        m.load("testfile1");
        m.save("testfile2");
        try {
        File file1 = new File("testfile1");
        File file2 = new File("testfile2");

        BufferedReader bfr1 = new BufferedReader(new FileReader(file1));
        BufferedReader bfr2 = new BufferedReader(new FileReader(file2));
        
        String y, z, s1 = "", s2 = "";
        while ((z = bfr1.readLine()) != null)
            s1 += z;

        while ((y = bfr2.readLine()) != null)
            s2 += y;
        bfr1.close();
        bfr2.close();
        if (s1.equals(s2))
        	System.out.println("yes");
        else
        	System.out.println("no");
        
        }
        catch(Exception e){
        	System.out.println(e);
        }
        */

    }

    // init du moteur a partir d'un tableau de joueur
    static public void initMoteur(ArrayList<Player> arrayPlayer){
        m = new Moteur(arrayPlayer);
    }



}

