package Cards;


// Carte pour choisir le role au debut de chaque manche

import java.util.ArrayList;

public class HandRole extends Hand {

    //indexé en ligne de 3 a 10 : Nombre Saboteur en premier puis le nombre
    //  de chercheur d'or / mineur
    private final int [][] rulePLayer = {{1, 3}, {1, 4}, {2, 4}, {2, 5}, {3, 5}, {3, 6}, {3, 7}, {4, 7}};


    public HandRole(){
        arrayCard = new ArrayList<Card>();

        int nbSaboteur = rulePLayer[0][0];
        int nbMinor = rulePLayer[0][1];

        fillHand(nbSaboteur, nbMinor);

        this.shuffle();
    }

    // init les cartes pour choisir le role en début de manches

    public HandRole(int n){
        int nbPlayer = n;

        arrayCard = new ArrayList<Card>();

        if(n > 10){
            nbPlayer = 10;
        } else if(n < 3){
            nbPlayer = 3;
        }

        int nbSaboteur = rulePLayer[nbPlayer-3][0];
        int nbMinor = rulePLayer[nbPlayer-3][1] - 1;

        fillHand(nbSaboteur, nbMinor);

        this.shuffle();
    }

    // rempli le paquet
    private void fillHand(int nbSaboteur, int nbMinor){

        for(int i=0; i<nbSaboteur; i++){
            arrayCard.add(new RoleCard("Saboteur"));
        }
        for(int i=0; i<nbMinor; i++){
            arrayCard.add(new RoleCard("Mineur"));
        }
    }
    
    public String toString(){
    	String me = "";
    	for (int i=0; i<this.arrayCard.size(); i++)
    		me += (this.arrayCard.get(i).toString() + "\n");
    	return me;
    }
}
