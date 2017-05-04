package Cards;


import java.util.ArrayList;

public class DeckPlayer extends Deck {

    DeckPlayer(){
        arrayCard = new ArrayList<Card>();
        for(int i=0; i<3; i++){
            arrayCard.add(new PlayerCard());
        }
    }

    DeckPlayer(int n){
        int nbPlayer = n;
        if(n > 10){
            nbPlayer = 10;
        } else if(n < 3){
            nbPlayer = 3;
        }
        arrayCard = new ArrayList<Card>();
        for(int i=0; i<nbPlayer; i++){
            arrayCard.add(new PlayerCard());
        }
    }
}
