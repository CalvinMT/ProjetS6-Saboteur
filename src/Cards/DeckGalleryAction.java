package Cards;

import java.util.ArrayList;
import java.util.Random;


public class DeckGalleryAction extends Deck {


    //Dépendant du jeu

    private final int nbCardAction = 27;

    private final int nbNegativPickaxe = 3;
    private final int nbNegativLantern = 3;
    private final int nbNegativWagon = 3;
    private final int nbPositivWagon = 2;
    private final int nbPositivPickaxe = 2;
    private final int nbPositivLantern = 2;
    private final int nbMap = 6;
    private final int nbCrash = 3;
    private final int nbWagonPickaxe = 1;
    private final int nbPickaxeLantern = 1;
    private final int nbWagonLantern = 1;


    private final int nbCardGallery = 40;
    final int nbCardGallery_no_centered = 9;
    private final int nbCardGallery_centered = nbCardGallery - nbCardGallery_no_centered;

    public int getNbBloqued(){
        return this.nbCardGallery_centered;
    }


    public DeckGalleryAction(){

        GalleryCard.Gallery_t type = GalleryCard.Gallery_t.tunnel;

        Random rand = new Random();

        this.arrayCard = new ArrayList<>();
        int nb = 0;
        GalleryCard c;
        while(nb<nbCardGallery_centered){
            c = new GalleryCard(type, true, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
            if(c.possible()){
                nb++;
                this.arrayCard.add(c);
            }
        }

        nb = 0;
        while(nb<nbCardGallery_no_centered){
            c =new GalleryCard(type, false, rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean(), rand.nextBoolean());
            if(c.possible()){
                nb++;
                this.arrayCard.add(c);
            }
        }

        // Ajout des cartes actions
        // Carte Sabotage : Lantern
        for(int i=0; i<nbNegativLantern; i++){
            this.arrayCard.add(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern));
        }

        // Carte Sabotage : Pickaxe
        for(int i=0; i<nbNegativPickaxe; i++){
            this.arrayCard.add(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe));
        }

        // Carte Sabotage : Wagon
        for(int i=0; i<nbNegativWagon; i++){
            this.arrayCard.add(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon));
        }

        // Carte Repare : Lantern
        for(int i=0; i<nbPositivLantern; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern));
        }

        // Carte Repare : Pickaxe
        for(int i=0; i<nbPositivPickaxe; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe));
        }

        // Carte Repare : Wagon
        for(int i=0; i<nbPositivWagon; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon));
        }

        // Carte Repare : Lantern + Pickaxe
        for(int i=0; i<nbPickaxeLantern; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe, RepareSabotageCard.Tools.Lantern));
        }

        // Carte Repare : Lantern + Wagon
        for(int i=0; i<nbWagonLantern; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Wagon));
        }

        // Carte Repare : Wagon + Pickaxe
        for(int i=0; i<nbWagonPickaxe; i++){
            this.arrayCard.add(new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon, RepareSabotageCard.Tools.Pickaxe));
        }

        // Carte Map
        for(int i=0; i<nbMap; i++){
            this.arrayCard.add(new ActionCard("Map"));
        }

        // Carte effondrement
        for(int i=0; i<nbCrash; i++){
            this.arrayCard.add(new ActionCard("Crumbing"));
        }


        this.shuffle();
        this.shuffle();
        this.shuffle();
        this.shuffle();

    }

    public boolean getValidity(){
        return nbCardAction == (nbNegativPickaxe + nbNegativLantern + nbNegativWagon + nbPositivWagon + nbPositivPickaxe + nbPositivLantern +
         nbMap + nbCrash + nbWagonPickaxe + nbPickaxeLantern + nbWagonLantern);

    }





}
