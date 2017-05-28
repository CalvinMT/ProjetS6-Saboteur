package Saboteur;



import Board.Board;
import Board.Node;
import Board.Couple;
import Cards.*;
import IHM.ChoixRole;
import IHM.GameInteract;
import Player.Player;
import Player.PlayerHuman;
import Player.IA;
import Saboteur.Moteur.State;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Load {

    Moteur engine_loaded;

    private ArrayList<Player> arrayPlayer;
    private Deck pile;
    private int currentPlayer = -1;
    private HandRole roleCards;
    private ArrayList<Boolean> roleTaken;
    private Board board;
    private long echeance;
    private ChoixRole choixroleControler;
    private GameInteract gameInteractControler;
    private State state;

    private int nb_saboters;
    private int nb_miners;

    private String fileToLoad;

    ///// LOAD ////

    public Load(String file){
        System.out.println("[LOAD] inits");
        this.fileToLoad = file;
    }

    public boolean load() {

        boolean succes = true;

        try {
            FileReader filereader = new FileReader(this.fileToLoad);
            BufferedReader br = new BufferedReader(filereader);
            String playerName, playerType, playerRole, tempGoldPoints, attr, temp;
            int goldPoints;


            // joueur
            if(!load_player(br)){
                return false;
            }
            this.roleCards =  new HandRole();
            this.roleCards.fillHand(nb_saboters, nb_miners);

            // setting the currentplayer
            temp = br.readLine();
            this.currentPlayer = Integer.parseInt(temp);
            if(this.currentPlayer < 0 || this.currentPlayer >= arrayPlayer.size()){
                return false;
            }

            System.out.println("Players loaded");

            // load deck
            if(!load_deck(br)){
                return false;
            }

            System.out.println("Deck loaded");

            // setting the mine
            temp = br.readLine();
            //System.out.println(temp);
            String[] mineCards = temp.split("[?]");

            if (!reinitBoard(mineCards)){
                return false;
            }

            System.out.println("Board loaded");

            br.close();


        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return true;
    }

    public String loadString(){

        String renvoi = "Moteur: \n";


        renvoi += "Joueur courant: " + this.arrayPlayer.get(this.currentPlayer).getPlayerName() + "\n";
        renvoi += "Joueurs [ ";
        for (int i = 0; i < arrayPlayer.size(); i++) {
            renvoi += this.arrayPlayer.get(i)+"\n";
        }
        renvoi += "]\n";

        renvoi += this.roleCards.print_without_visibility() + "\n\n";
        renvoi += "Deck: " + this.pile.nbCard() + " cartes \n";
        renvoi += this.board.mine() + "\n";

        return renvoi;


    }




    /// LOAD PLAYERS

    public boolean load_player(BufferedReader br){

        nb_miners = 0;
        nb_saboters = 0;

        this.arrayPlayer = new ArrayList<Player>();

        String temp, playerType, playerRole, tempGoldPoints, attr;
        int goldPoints;

        try {

            // lecture du nombre de joueurs
            temp = br.readLine();
//            System.out.println("Temp: "+temp);
            if (!temp.matches("([3-9])|(10)")){
                return false;
            }
            int nbPlayers = Integer.parseInt(temp);

            Player player;

//            System.out.println("Nbplayers: "+nbPlayers);

            // création des joueurs enregistrés
            for (int i = 0; i < nbPlayers; i++) {
                String playerName = br.readLine();

                System.out.println("PLayername: "+playerName);

                playerType = br.readLine();
                if (playerType.equals("Player")) {

                    player = new PlayerHuman(i, playerName);

                } else if (playerType.equals("IA")) {
                    temp = br.readLine();
                    if (temp.equals("Easy"))
                    player = new IA(i, playerName, Player.Difficulty.Easy);
                    else if (temp.equals("Medium"))
                    player = new IA(i, playerName, Player.Difficulty.Medium);
                    else if (temp.equals("Hard"))
                    player = new IA(i, playerName, Player.Difficulty.Hard);
                    else{
                        return false;
                    }
                } else {
                    return false;
                }


                playerRole = br.readLine();
                Card rolecard;

                if (playerRole.equals("Mineur")) {
                    rolecard = new RoleCard("Mineur");
                nb_miners++;
                } else if (playerRole.equals("Saboteur")) {
                    rolecard = new RoleCard("Saboteur");
                nb_saboters++;
                } else {
                    return false;
                }

                player.assignRole(rolecard);

                tempGoldPoints = br.readLine(); // System.out.println(player.getRole());
                if (tempGoldPoints.matches("[0-9]+")) {
                    goldPoints = Integer.parseInt(tempGoldPoints);
                    player.setGoldPoints(goldPoints);
                } else {
                    return false;
                }

                String[] handCards;
                String[] attrCards;
                attr = br.readLine();
                // attr sera vide si le joueur n'a pas de cartes Attribute

                if (!attr.equals("")) {
                    attrCards = attr.split("[;]");
                    if (attrCards.length > 3)
                    return false;
                    for (int k = 0; k < attrCards.length; k++) {
                        String[] kCard = attrCards[k].split("[:]");
                        if (!kCard[0].equals("Action") && kCard.length != 2)
                        return false;
                        if (kCard[1].equals("Sabotage{Lantern}")) {
                            player.putSabotage(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern), player);
                        } else if (kCard[1].equals("Sabotage{Wagon}"))
                        player.putSabotage(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon), player);
                        else if (kCard[1].equals("Sabotage{Pickaxe}"))
                        player.putSabotage(new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe), player);
                        else
                        return false;
                    }
                }


                temp = br.readLine();

                handCards = temp.split("[;]");

                // test for appropriate number of handCards for the given number of players
                if (nbPlayers >= 3 && nbPlayers < 6 && handCards.length > 6)
                return false;
                if ((nbPlayers == 6 || nbPlayers == 7) && handCards.length > 5)
                return false;
                if ((nbPlayers == 8 || nbPlayers == 9 || nbPlayers == 10) && handCards.length > 4)
                return false;

                if (!reinitSavedPlayerHand(handCards, player)){

                    //TODO fix that !!!!
                    System.out.println("Bug ici");
                    return false;
                }
                this.arrayPlayer.add(player);

                temp = br.readLine();

                System.out.printf(playerName);

            }

        } catch (IOException e) {
            System.err.println("[LOAD] IOExcepion");
            e.printStackTrace();
        } catch (Exception ex){
            System.err.println("[LOAD] Autre exception: "+ex);
            ex.printStackTrace();
        }
        return true;

}

    /// LOAD DECK

    public boolean load_deck(BufferedReader br){
        // to get the number of cards remaining in the Deck

        String temp;

        try {

            temp = br.readLine();
            int deckSize;
            if (!temp.matches("[0-9]+"))
            return false;
            deckSize = Integer.parseInt(temp);

            if(deckSize < 0){
                return false;
            }


            // setting the Deck

            temp = br.readLine();
            String[] savedDeck = temp.split("[;]");
            DeckGalleryAction tempDeck = new DeckGalleryAction(deckSize);

            boolean goodDeck = true;
            if (!reinitSavedDeck(savedDeck, tempDeck)) {
                goodDeck = false;
            }

            if (goodDeck){
                this.pile = tempDeck; // System.out.println("Good deck");
            } else {
                return false;
            }

        } catch (IOException e) {
            System.err.println("[LOAD] IOExcepion");
        e.printStackTrace();
        } catch (Exception ex){
            System.err.println("[LOAD] Autre exception: "+ex);
        ex.printStackTrace();
        }
        return true;

}

    public boolean reinitSavedDeck(String[] handCards, DeckGalleryAction deck) {
        int x, y;
        String[] tempSplit;
        boolean north = false, east = false, south = false, west = false, center = false;
        String actionType;

//        boolean [] tab_bool = new boolean[5];

        for (int j = 0; j < handCards.length; j++) {
            GalleryCard gallerycard = null;
            ActionCard actioncard = null;
            RepareSabotageCard repair = null, sabotage = null;
            String[] eachCard = handCards[j].split("[:]");

            switch (eachCard[0]) {

                /// GALLERY CARD
                case "GalleryCard":

                    tempSplit = eachCard[1].split(",");
                    if (tempSplit.length != 8)
                        return false;

                    // essai de factorisation
                    /*for(int i=3; i<8; i++){
                        if(tempSplit[i].equals("true")){
                            tab_bool[i-3] = true;
                        } else if(tempSplit[i].equals("false")){
                            tab_bool[i-3] = false;
                        } else {
                            return false;
                        }
                    }*/

                    if (tempSplit[1].matches("[0-9]+") && tempSplit[2].matches("[0-9]+")) {
                        x = Integer.parseInt(tempSplit[1]);
                        y = Integer.parseInt(tempSplit[2]);
                    } else {
                        return false;
                    }

                    if (tempSplit[3].equals("true"))
                        center = true;
                    else if (tempSplit[3].equals("false"))
                        center = false;
                    else
                        return false;

                    if (tempSplit[4].equals("true"))
                        north = true;
                    else if (tempSplit[4].equals("false"))
                        north = false;
                    else
                        return false;

                    if (tempSplit[5].equals("true"))
                        south = true;
                    else if (tempSplit[5].equals("false"))
                        south = false;
                    else
                        return false;

                    if (tempSplit[6].equals("true"))
                        east = true;
                    else if (tempSplit[6].equals("false"))
                        east = false;
                    else
                        return false;

                    if (tempSplit[7].equals("true}"))
                        west = true;
                    else if (tempSplit[7].equals("false}"))
                        west = false;
                    else
                        return false;



//                    gallerycard = new GalleryCard(GalleryCard.Gallery_t.tunnel, x, y, tab_bool[0], tab_bool[1], tab_bool[2], tab_bool[3], tab_bool[4]);
                    gallerycard = new GalleryCard(GalleryCard.Gallery_t.tunnel, x, y, center, north, south, east, west);
                    deck.addCardToDeck(gallerycard);
                    break;

                //// ACTION CARD

                case "Action":

                    if (eachCard[1].equals("Map") || eachCard[1].equals("Crumbing")) {
                        actionType = eachCard[1];
                        actioncard = new ActionCard(actionType);
                    } else if (eachCard[1].matches("(R|S)[a-z]+\\{((W|L|P)[a-z]+,)?(W|L|P)[a-z]+\\}")) {
                        tempSplit = eachCard[1].split("[{]");
                        if (tempSplit[0].equals("Sabotage")) {
                            actionType = tempSplit[0];
                            if (tempSplit[1].equals("Lantern}"))
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
                            else if (tempSplit[1].equals("Wagon}"))
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Pickaxe}")) {
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
                            } else
                                return false;
                        } else if (tempSplit[0].equals("Repare")) {
                            actionType = tempSplit[0];
                            if (tempSplit[1].equals("Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern);
                            else if (tempSplit[1].equals("Wagon}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Pickaxe}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe);
                            else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Pickaxe);
                            else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe, RepareSabotageCard.Tools.Wagon);
                            else
                                return false;
                        } else
                            return false;
                    } else
                        return false;

                    if (actioncard != null) {
                        deck.addCardToDeck(actioncard);
                    }
                    if (repair != null)
                        deck.addCardToDeck(repair);
                    if (sabotage != null)
                        deck.addCardToDeck(sabotage);

                    break;
                default:
                    return false;
            }

        }
        return true;

    }

    public boolean reinitSavedPlayerHand(String[] handCards, Player player) {
        int x, y;
        String[] tempSplit;
        boolean north = false, east = false, south = false, west = false, center = false;
        String actionType;

        for (int j = 0; j < handCards.length; j++) {
            GalleryCard gallerycard = null;
            ActionCard actioncard = null;
            RepareSabotageCard repair = null, sabotage = null;
            String[] eachCard = handCards[j].split("[:]");

            // System.out.println(j);

            switch (eachCard[0]) {
                case "GalleryCard":
                    tempSplit = eachCard[1].split(",");
                    if (tempSplit.length != 8)
                        return false;

                    if (tempSplit[1].matches("[0-9]+") && tempSplit[2].matches("[0-9]+")) {
                        x = Integer.parseInt(tempSplit[1]);
                        y = Integer.parseInt(tempSplit[2]);

                    } else
                        return false;
                    if (tempSplit[3].equals("true"))
                        center = true;
                    else if (tempSplit[3].equals("false"))
                        center = false;
                    else
                        return false;

                    if (tempSplit[4].equals("true"))
                        north = true;
                    else if (tempSplit[4].equals("false"))
                        north = false;
                    else
                        return false;

                    if (tempSplit[5].equals("true"))
                        south = true;
                    else if (tempSplit[5].equals("false"))
                        south = false;
                    else
                        return false;

                    if (tempSplit[6].equals("true"))
                        east = true;
                    else if (tempSplit[6].equals("false"))
                        east = false;
                    else
                        return false;

                    if (tempSplit[7].equals("true}"))
                        west = true;
                    else if (tempSplit[7].equals("false}"))
                        west = false;
                    else
                        return false;
                    gallerycard = new GalleryCard(GalleryCard.Gallery_t.tunnel, x, y, center, north, south, east, west);
                    player.drawCard(gallerycard);

                    break;

                case "Action":
                    if (eachCard[1].equals("Map") || eachCard[1].equals("Crumbing")) {
                        actionType = eachCard[1]; // System.out.println(actionType);
                        actioncard = new ActionCard(actionType);
                    } else if (eachCard[1].matches("(R|S)[a-z]+\\{((W|L|P)[a-z]+,)?(W|L|P)[a-z]+\\}")) {
                        tempSplit = eachCard[1].split("[{]");
                        if (tempSplit[0].equals("Sabotage")) {
                            actionType = tempSplit[0];
                            if (tempSplit[1].equals("Lantern}"))
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
                            else if (tempSplit[1].equals("Wagon}"))
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Pickaxe}")) {
                                sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
                            } else
                                return false;
                        } else if (tempSplit[0].equals("Repare")) {
                            actionType = tempSplit[0];
                            if (tempSplit[1].equals("Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern);
                            else if (tempSplit[1].equals("Wagon}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Pickaxe}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe);
                            else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Wagon);
                            else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Pickaxe);
                            else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                                repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe, RepareSabotageCard.Tools.Wagon);
                            else
                                return false;
                        } else
                            return false;

                    } else
                        return false;

                    if (actioncard != null) {
                        player.drawCard(actioncard);
                        // System.out.println("me is "+ j + actioncard);
                    }
                    if (repair != null)
                        player.drawCard(repair);
                    if (sabotage != null)
                        player.drawCard(sabotage);

                    break;
                default:
                    System.out.println("Default");
                    return false;
            }
        }

        System.out.println(player);
        return true;
    }


    public boolean reinitBoard(String[] boardCards) {
        int north = 0, south = 0, east = 0, west = 0;
        String[] indexes;
        GalleryCard gc;
        Node node = null;
        this.board = new Board();

        for (int i = 0; i < boardCards.length; i++) {
            if (boardCards[i].substring(0, 8).equals("GoalCard")) {
                String[] tempSplit = boardCards[i].split("[:]");

                // test pour les GoalCards
                boolean isgold = false;
                if (tempSplit[1].equals("false"))
                    isgold = false;
                else if (tempSplit[1].equals("true"))
                    isgold = true;
                else
                    return false;
                if (!tempSplit[2].equals("GalleryCard"))
                    return false;



                String gallery = tempSplit[2] + ":" + tempSplit[3];
                // System.out.println("gallery : "+ gallery);
                try {
                    gc = createCard(gallery);
                    boolean n = gc.getNorth(), e = gc.getEast(), w = gc.getWest(), s = gc.getSouth();
                    int x = gc.getLine(), y = gc.getColumn();
                    GoalCard but = new GoalCard(new Couple(x,y), n, s, e, w, isgold);
                    node = new Node(but);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                //pour recupérer les indices du node
                gallery = tempSplit[4];

                indexes = gallery.split(",");

                //for (int j=0; j<indexes.length; j++) System.out.println(indexes[j]);
                //System.out.println(gallery);

                if (indexes[0].matches("-?[0-9]+")) {
                    north = Integer.parseInt(indexes[0]);
                    node.setNorth(north);
                }
                else
                    return false;
                if (indexes[1].matches("-?[0-9]+")) {
                    east = Integer.parseInt(indexes[1]);
                    node.setEast(east);
                }
                else
                    return false;
                if (indexes[2].matches("-?[0-9]+")) {
                    west = Integer.parseInt(indexes[2]);
                    node.setWest(west);
                }
                else
                    return false;
                if (indexes[3].matches("-?[0-9]+")) {
                    south = Integer.parseInt(indexes[3]);
                    node.setSouth(south);
                }
                else
                    return false;
                this.board.getMine().add(node);
            }

            else if (boardCards[i].substring(0, 5).equals("Start")) {
                gc = new GalleryCard();
                node = new Node(gc);
                String temp = boardCards[i].substring(6, 17);
                //System.out.println(temp);
                String[] startIndexes = temp.split(",");
                if (startIndexes[0].matches("-?[0-9]+")) {
                    north = Integer.parseInt(startIndexes[0]);
                    node.setNorth(north);
                }
                else
                    return false;
                if (startIndexes[1].matches("-?[0-9]+")) {
                    east = Integer.parseInt(startIndexes[1]);
                    node.setEast(east);
                }
                else
                    return false;
                if (startIndexes[2].matches("-?[0-9]+")) {
                    west = Integer.parseInt(startIndexes[2]);
                    node.setWest(west);
                }
                else
                    return false;
                if (startIndexes[3].matches("-?[0-9]+")) {
                    south = Integer.parseInt(startIndexes[3]);
                    node.setSouth(south);
                }
                else
                    return false;
                this.board.getMine().add(node);
            }
            // next type of card check based on text file
            else if (boardCards[i].substring(0, 12).equals("GalleryCard:")) {

                String[] str = boardCards[i].split(":");  // str1 = GalleryCard:{gallery,0,8,true,false,false,true,true}
                String  str1 = str[0] + ":" + str[1];
                try {
                    gc = createCard(str1);
                    node = new Node(gc);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String[] cardIndexes = str[2].split(","); // example of str[2] format :  str[2] = -1,-1,-1,-1
                if (cardIndexes[0].matches("-?[0-9]+")) {
                    north = Integer.parseInt(cardIndexes[0]);
                    node.setNorth(north);
                }
                else
                    return false;
                if (cardIndexes[1].matches("-?[0-9]+")) {
                    east = Integer.parseInt(cardIndexes[1]);
                    node.setEast(east);
                }
                else
                    return false;
                if (cardIndexes[2].matches("-?[0-9]+")) {
                    west = Integer.parseInt(cardIndexes[2]);
                    node.setWest(west);
                }
                else
                    return false;
                if (cardIndexes[3].matches("-?[0-9]+")) {
                    south = Integer.parseInt(cardIndexes[3]);
                    node.setSouth(south);
                }
                else
                    return false;
                this.board.getMine().add(node);

            }
            else
                return false;
        }
        return true;
    }


    public GalleryCard createCard(String handCards) throws Exception {
        int x, y;
        String[] tempSplit;
        boolean north = false, east = false, south = false, west = false, center = false;
        String actionType;

        GalleryCard gallerycard = null;
        ActionCard actioncard = null;
        RepareSabotageCard repair = null, sabotage = null;
        String[] eachCard = handCards.split("[:]");

        switch (eachCard[0]) {
            case "GalleryCard":
                tempSplit = eachCard[1].split(",");
                if (tempSplit.length != 8)
                    throw new Exception("Incorrect number of constructor arguments for a GalleryCard!");

                if (tempSplit[1].matches("-?[0-9]+") && tempSplit[2].matches("-?[0-9]+")) {
                    x = Integer.parseInt(tempSplit[1]);
                    y = Integer.parseInt(tempSplit[2]);
                } else
                    throw new Exception("Incorrect format of Couple arguments for a GalleryCard!");

                // System.out.println("x = " + x + "; y = " + y);

                if (tempSplit[3].equals("true"))
                    center = true;
                else if (tempSplit[3].equals("false"))
                    center = false;
                else
                    throw new Exception("Incorrect boolean format for a GalleryCard!");

                if (tempSplit[4].equals("true"))
                    north = true;
                else if (tempSplit[4].equals("false"))
                    north = false;
                else
                    throw new Exception("Incorrect boolean format for a GalleryCard!");

                if (tempSplit[5].equals("true"))
                    south = true;
                else if (tempSplit[5].equals("false"))
                    south = false;
                else
                    throw new Exception("Incorrect boolean format for a GalleryCard!");

                if (tempSplit[6].equals("true"))
                    east = true;
                else if (tempSplit[6].equals("false"))
                    east = false;
                else
                    throw new Exception("Incorrect boolean format for a GalleryCard!");

                if (tempSplit[7].equals("true}"))
                    west = true;
                else if (tempSplit[7].equals("false}"))
                    west = false;
                else
                    throw new Exception("Incorrect boolean format for a GalleryCard!");
                gallerycard = new GalleryCard(GalleryCard.Gallery_t.tunnel, x, y, center, north, south, east, west);
                // System.out.println(gallerycard);
                break;

            case "Action":
                if (eachCard[1].equals("Map") || eachCard[1].equals("Crumbing")) {
                    actionType = eachCard[1];
                    actioncard = new ActionCard(actionType);
                } else if (eachCard[1].matches("(R|S)[a-z]+\\{((W|L|P)[a-z]+,)?(W|L|P)[a-z]+\\}")) {
                    tempSplit = eachCard[1].split("[{]");
                    if (tempSplit[0].equals("Sabotage")) {
                        actionType = tempSplit[0];
                        if (tempSplit[1].equals("Lantern}"))
                            sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}")) {
                            sabotage = new RepareSabotageCard("Sabotage", RepareSabotageCard.Tools.Pickaxe);
                        } else
                            throw new Exception("Incorrect tool format for an ActionCard!");
                    } else if (tempSplit[0].equals("Repare")) {
                        actionType = tempSplit[0];
                        if (tempSplit[1].equals("Lantern}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe);
                        else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Wagon);
                        else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Lantern, RepareSabotageCard.Tools.Pickaxe);
                        else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", RepareSabotageCard.Tools.Pickaxe, RepareSabotageCard.Tools.Wagon);
                        else
                            throw new Exception("Incorrect tool format for an ActionCard!");
                    } else
                        throw new Exception("Incorrect action type for an ActionCard!");

                } else
                    throw new Exception("Incorrect text format for an ActionCard!");

                break;

            default:
                throw new Exception("Incorrect Card Type exception!");
        }
        if (gallerycard != null)
            return gallerycard;

        return null;
    }


}
