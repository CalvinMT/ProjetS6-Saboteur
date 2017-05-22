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
            this.arrayCard.add(new GoldCard(3));
            //TODO
        }

        for(int i=0; i<twoGold; i++){
            this.arrayCard.add(new GoldCard(2));
            //TODO
        }

        for(int i=0; i<oneGold; i++){
            this.arrayCard.add(new GoldCard(1));
            //TODO
        }
    }

    // test validité
    public boolean nbCard_true(){
        return nbCardGold == (oneGold + twoGold + threeGold);
    }

    public ArrayList<Card> getArrayCard() {
        return this.arrayCard;
    }

    public int chooseGoldCard(int val){
        if (val > 0 && val < 4) {
            if (!this.arrayCard.isEmpty()){
                for (int i = 0; i < this.arrayCard.size(); i++) {
                    Card goldcard = this.arrayCard.get(i);
                    if (goldcard.getGold() == val) {
                        int returnVal = goldcard.getGold();
                        this.arrayCard.remove(i);
                        return returnVal;
                    }
                }
                // arrivant ici indique qu'il n'y a plus de GoldCard de type val
                return 0;
            }
            else {
                System.out.println("Il n'y a plus de GoldCard!");
            	return 0;
            }
        }
        else {
            System.err.println("Valeur doit etre entre 1 et 3");
            return 0;
        }        

    }
    // TODO
}
