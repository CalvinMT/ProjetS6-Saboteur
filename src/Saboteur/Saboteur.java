package Saboteur;


import Cards.RepareSabotageCard;

public class Saboteur {

    static public void main(String [] args){

        int nbPlayer = 4;
        Moteur m = new Moteur(nbPlayer);

        try {
            System.out.println(m);
            m.chooseRole(0);
            m.chooseRole(0);
            m.chooseRole(0);
            m.chooseRole(0);

            m.getPlayer(0).setAttributeCards(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern));
            m.getPlayer(0).setAttributeCards(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon));
            m.getPlayer(2).setAttributeCards(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern));

            m.getPlayer(2).putAttribute(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern), m.getPlayer(1));

            m.initHand();
            m.promptPlayers();
            System.out.println(m);
        } catch(Exception e){
            System.err.println("Erreur choix du role");
        }




    }



}

