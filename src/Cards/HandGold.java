package Cards;

import java.util.ArrayList;

// a la fin de la partie le choix de l'or

public class HandGold extends Hand {

    private final int threeGold = 4;
    private final int twoGold = 8;
    private final int oneGold = 16;
    private final int nbCardGold = 28;

    HandGold(){
        this.visible = true;

        this.arrayCard = new ArrayList<Card>();

        for(int i=0; i<threeGold; i++){
            // this.arrayCard.add(new GoldCard("Three");
            //TODO
        }

        for(int i=0; i<twoGold; i++){
            // this.arrayCard.add(new GoldCard("Two");
            //TODO
        }

        for(int i=0; i<oneGold; i++){
            // this.arrayCard.add(new GoldCard("One");
            //TODO
        }
    }

    // test validité
    public boolean nbCard_true(){
        return nbCardGold == (oneGold + twoGold + threeGold);
    }


    // TODO
}
