package IHM;


import Cards.ActionCard;
import Cards.Card;
import Cards.GalleryCard;
import Cards.RepareSabotageCard;

public class ImageCard {

    // TODO FACTORISER @Copyright G - Huard 2017
    // TODO getConfig

    static public GamePlayingCard getImageCard (Card c) {
        GamePlayingCard playingCard = null;
        switch(c.getType()) {
            case action:
                switch(((ActionCard)c).getAction()){
                    case Sabotage:
                        switch(((RepareSabotageCard)c).getTool()){
                            case Pickaxe:
                                playingCard = new GamePlayingCard("carte_brise_pioche");
                                break;
                            case Lantern:
                                playingCard = new GamePlayingCard("carte_brise_lanterne");
                                break;
                            case Wagon:
                                playingCard = new GamePlayingCard("carte_brise_chariot");
                                break;
                        }
                        break;
                    case Map:
                        playingCard = new GamePlayingCard("carte_plan_secret");
                        break;
                    case Repare:
                        switch(((RepareSabotageCard)c).nbTools()){
                            case 1 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                        playingCard = new GamePlayingCard("carte_repare_pioche");
                                        break;
                                    case Lantern:
                                        playingCard = new GamePlayingCard("carte_repare_lanterne");
                                        break;
                                    case Wagon:
                                        playingCard = new GamePlayingCard("carte_repare_chariot");
                                        break;
                                }
                                break;
                            case 2 :
                                switch(((RepareSabotageCard)c).getTool()){
                                    case Pickaxe:
                                        playingCard = new GamePlayingCard("carte_repare_pioche_lanterne");
                                        break;
                                    case Lantern:
                                        playingCard = new GamePlayingCard("carte_repare_lanterne_chariot");
                                        break;
                                    case Wagon:
                                        playingCard = new GamePlayingCard("carte_repare_chariot_pioche");
                                        break;
                                }
                                break;
                            default:
                                playingCard = new GamePlayingCard("carte_test_118_181");
                        }
                        break;
                    case Crumbling:
                        playingCard = new GamePlayingCard("carte_eboulement");
                        break;
                    default:
                        playingCard = new GamePlayingCard("carte_test_118_181");
                }
                break;

            /// VERIFIED BY THESPYGEEK

            case gallery:

                // carte start
                if(((GalleryCard)c).getGalleryType() == GalleryCard.Gallery_t.start){
                    playingCard = new GamePlayingCard("carte_depart");

                    // carte But
                } else if(((GalleryCard)c).getGalleryType() == GalleryCard.Gallery_t.but){
                    if(((GalleryCard)c).isGold()){
                        playingCard = new GamePlayingCard("carte_arrivee_0");
                    } else {

                        if(((GalleryCard)c).canHasNorth() && ((GalleryCard)c).canHasWest()){
                            playingCard = new GamePlayingCard("carte_arrivee_1");
                            playingCard.getImageView().setRotate(180);
                        } else if(((GalleryCard)c).canHasSouth() && ((GalleryCard)c).canHasEast() ){
                            playingCard = new GamePlayingCard("carte_arrivee_1");
                        } else if(((GalleryCard)c).canHasNorth() && ((GalleryCard)c).canHasEast()) {
                            playingCard = new GamePlayingCard("carte_arrivee_2");
                            playingCard.getImageView().setRotate(180);
                        } else if (((GalleryCard)c).canHasSouth() && ((GalleryCard)c).canHasWest() ){
                            playingCard = new GamePlayingCard("carte_arrivee_2");
                        } else {
                            playingCard = new GamePlayingCard("carte_test_118_18");
                        }

                    }


                    // carte tunnel
                } else {

                    if(((GalleryCard)c).canHasCenter()){
                        if(((GalleryCard)c).canHasNorth()){
                            if(((GalleryCard)c).canHasWest()){
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NSEO_C");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NSO_C");
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NEO_C");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NO_C");
                                    }
                                }
                            }else{//Sans West
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NSO_C");//NSE
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NS_C");
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NE_C");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("carte_test_118_181");//N
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }
                            }
                        }else{//Sans North
                            if(((GalleryCard)c).canHasWest()){
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NEO_C");//SEO
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NE_C");//SO
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("EO_C");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("carte_test_118_181");//O
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }
                            }else{//Sans West
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NO_C");//SE
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East carte_test_118_181
                                        playingCard = new GamePlayingCard("carte_test_118_181");//S
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("carte_test_118_181");//E
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }
                            }

                        }
                    }else{//Sans Center
                        if(((GalleryCard)c).canHasNorth()){
                            if(((GalleryCard)c).canHasWest()){
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NSEO_NC");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NSO_NC");
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NEO_NC");
                                    }else{
                                        playingCard = new GamePlayingCard("NO_NC");
                                    }
                                }
                            }else{//Sans West
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NSO_NC");//NSE
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("NS_NC");
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("SO_NC");
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("N_NC");//N
                                    }
                                }
                            }
                        }else{//Sans North
                            if(((GalleryCard)c).canHasWest()){
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NEO_NC");//SEO
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("SO_NC");//SO
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("EO_NC");
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("O_NC");
                                    }
                                }
                            }else{//Sans West
                                if(((GalleryCard)c).canHasSouth()){
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("NO_NC");//SE
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("N_NC");//S
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }else{//Sans South
                                    if(((GalleryCard)c).canHasEast()){
                                        playingCard = new GamePlayingCard("O_NC");//E
                                        playingCard.getImageView().setRotate(180);
                                    }else{//Sans East
                                        playingCard = new GamePlayingCard("carte_test_118_181");//rien
                                        playingCard.getImageView().setRotate(180);
                                    }
                                }
                            }
                        }
                    }
                }

                break;
            default:
                playingCard = new GamePlayingCard("carte_test_118_181");
        }
        return playingCard;
    }

}
