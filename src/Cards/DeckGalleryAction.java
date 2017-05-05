package Cards;

import java.util.ArrayList;
import java.util.Random;


public class DeckGalleryAction extends Deck {


    /*DeckGalleryAction(int n){
        int nbCard = n;
        /// a changer selon les cartes que l'on met
        if(nbCard <= 0){
            nbCard = 44;
        }
        this.arrayCard = new ArrayList<Card>();
        for(int i=0; i<nbCard; i++){
            this.arrayCard.add(new ActionCard());
        }
    }*/

    public DeckGalleryAction(){
        final int nbCardGallery = 40;
        final int nbCardAction = 27;
        final int nbCardGallery_centered = 8;
        final int nbCardGallery_no_centered = nbCardGallery - nbCardGallery_centered;

        GalleryCard.Gallery_t type = GalleryCard.Gallery_t.tunnel;

        Random rand = new Random();

        this.arrayCard = new ArrayList<Card>();

        for(int i=0; i<nbCardGallery_centered; i++){

            this.arrayCard.add(new GalleryCard(type, true, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        }

        for(int i=0; i<nbCardGallery_no_centered; i++){
            this.arrayCard.add(new GalleryCard(type, false, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean()));
        }

        for(int i=0; i<nbCardAction; i++){
            this.arrayCard.add(new ActionCard());
        }

        this.shuffle();
        this.shuffle();

    }







}
