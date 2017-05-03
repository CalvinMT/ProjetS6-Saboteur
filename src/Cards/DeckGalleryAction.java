package Cards;

import java.util.ArrayList;


public class DeckGalleryAction extends Deck {


    DeckGalleryAction(int n){
        int nbCard = n;
        /// a changer selon les cartes que l'on met
        if(nbCard <= 0){
            nbCard = 44;
        }
        this.arrayCard = new ArrayList<Card>();
        for(int i=0; i<nbCard; i++){
            this.arrayCard.add(new ActionCard());
        }
    }

    DeckGalleryAction(){
        this.arrayCard = new ArrayList<Card>();
        for(int i=0; i<44; i++){
            this.arrayCard.add(new ActionCard());
        }
    }







}
