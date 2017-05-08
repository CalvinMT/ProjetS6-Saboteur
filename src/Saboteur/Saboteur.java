package Saboteur;



public class Saboteur {

    static public void main(String [] args){

        int nbPlayer = 5;
        Moteur m = new Moteur(nbPlayer);

        try {
            m.chooseRole(0, 2);

            m.initHand();
            m.promptPlayers();
//            System.out.println(m);
        } catch(Exception e){
            System.err.println("Erreur choix du role");
        }




    }



}

