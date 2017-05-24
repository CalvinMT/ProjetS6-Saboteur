/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Saboteur;

import Cards.*;
import Cards.GalleryCard.Gallery_t;
import Cards.RepareSabotageCard.Tools;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import Player.*;
import Player.Player.Difficulty;
import Board.Board;
import Board.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 *
 * @author uwalakae
 */
public class Moteur {
    private ArrayList<Player> arrayPlayer;
    private Deck pile;
    private int currentPlayer;
    private HandRole roleCards;

    // ajout du board
    Board board;

    // ligne nbJoueur colonne Nb carte de 0 à 7 contenant le nombre de cartes en
    // début de partie
    final int[] ruleNbCard = { 6, 6, 6, 5, 5, 4, 4, 4 };

    // constructeur
    public Moteur(int nbPlayer) {
        arrayPlayer = new ArrayList<Player>();
        this.pile = new DeckGalleryAction();
        initArrayPlayer(nbPlayer);
        currentPlayer = 0;
        roleCards = new HandRole(nbPlayer());
        this.board = new Board();
    }

    public Moteur(ArrayList<Player> arrayPlayer) {
        this.arrayPlayer = arrayPlayer;
        this.pile = new DeckGalleryAction();
        currentPlayer = 0;
        roleCards = new HandRole(nbPlayer());
        this.board = new Board();

        setAllPlayerBoard();

        System.out.println("Partie configurée!");
    }

    public Moteur(String filename) {
        this.board = new Board(0);
        this.arrayPlayer = new ArrayList<Player>();
        this.pile = new DeckGalleryAction(0);
        this.roleCards = new HandRole(filename);
        this.currentPlayer = 0;
        boolean yes = this.load(filename);
    }

    public void setAllPlayerBoard() {
        for (int i = 0; i < nbPlayer(); i++) {
            arrayPlayer.get(i).setBoard(this.board);
        }
    }

    // initialise le tableau contenant les joueurs
    private void initArrayPlayer(int n) {
        int nbPlayer;

        if (n < 3 || n > 10) {
            nbPlayer = 3;
        } else {
            nbPlayer = n;
        }

        for (int i = 0; i < nbPlayer; i++) {
            arrayPlayer.add(new PlayerHuman(i + 1, this.board));
        }
    }

    // fait piocher le bon nombre de carte a tous les joueurs
    public void initHand() {

        int nbCard = ruleNbCard[this.nbPlayer() - 3];

        for (int i = 0; i < nbPlayer(); i++) {
            for (int j = 0; j < nbCard; j++) {
                arrayPlayer.get(i).drawCard(this.pile);
            }
        }

    }

    // choix des roles en début de manche
    public void chooseRole(int numPlayer, int numCard) throws Exception {

        if (!this.roleCards.isEmpty()) {
            if (numCard >= 0 && numCard < this.roleCards.nbCard() && numPlayer >= 0 && numPlayer < nbPlayer()) {
                Card c = this.roleCards.chooseOne_with_remove(numCard);
                this.arrayPlayer.get(numPlayer).assignRole(c);
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    // choix des roles en début de manche
    public void chooseRole(int numCard) throws Exception {

        if (!this.roleCards.isEmpty()) {
            if (numCard >= 0 && numCard < this.roleCards.nbCard() && this.currentPlayer >= 0
                    && this.currentPlayer < nbPlayer()) {
                Card c = this.roleCards.chooseOne_with_remove(numCard);

                this.arrayPlayer.get(this.currentPlayer).assignRole(c);
                nextPlayer();
            } else {
                throw new Exception();
            }
        } else {
            throw new Exception();
        }
    }

    // si tous les roles sont attribués
    public boolean allRoleAreSet() {
        for (int i = 0; i < nbPlayer(); i++) {
            if (this.arrayPlayer.get(i).getRole() == null) {
                return false;
            }
        }
        return true;
    }

    // affiche les infos joueurs en version texte
    public void promptPlayers() {
        for (int i = 0; i < nbPlayer(); i++) {
            System.out.println(arrayPlayer.get(i));
            System.out.println();
        }
    }

    // passe au joueur suivant
    public void nextPlayer() {
        currentPlayer = (currentPlayer + 1) % nbPlayer();
    }

    // renvoie le nombre max de cartes que les joueurs peuvent avoir en main
    public int maxHandCard() {
        return ruleNbCard[nbPlayer() - 3];
    }

    // renvoie le nombre de joueur
    public int nbPlayer() {
        return arrayPlayer.size();
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < nbPlayer()) {
            return arrayPlayer.get(i);
        } else {
            return null;
        }
    }

    // le joueur courant joue une carte sur le board
    public void play(GalleryCard c) {
        if (c.getType() == Card.Card_t.gallery) {

            // TODO a test
            // ajout dans sur le board
            this.board.addCard(c);
            nextPlayer();
        }
    }

    // le joueur courant joue une carte revele goal ou effondrement
    public void play(ActionCard c, Couple cou) {
        if (c.getType() == Card.Card_t.action) {
            if (c.getAction() == ActionCard.Action.Map) {
                Card goal = this.lookGoal(cou);
                System.out.println("Carte goal: " + goal);
                nextPlayer();
            } else if (c.getAction() == ActionCard.Action.Crumbing) {

                // TODO a test
                this.board.removeCard(cou);
                nextPlayer();
            }
        }
    }

    // le joueur courant joue une carte Sabotage
    public void play(RepareSabotageCard c, Player p) {
        if (c.getType() == Card.Card_t.action) {
            if (c.getAction() == ActionCard.Action.Sabotage) {
                this.getCurrentPlayer().putSabotage(c, p);
                nextPlayer();
            }
        }
    }

    // le joueur courant joue une carte Repare sur un joueur
    public void play(RepareSabotageCard c, RepareSabotageCard.Tools t, Player p) {
        if (c.getType() == Card.Card_t.action) {
            if (c.getAction() == ActionCard.Action.Repare) {
                this.getCurrentPlayer().putRepare(c, t, p);
                nextPlayer();
            }
        }

    }

    // regarde la carte but choisi par le joueur
    public Card lookGoal(Couple c) {
        if (c.getY() == 8 && (c.getX() == 0 || c.getX() == 2 || c.getX() == -2)) {
            System.out.println("Taille mine: " + this.board.getMineSize());
            if (c.getX() == 2) { // B3
                return this.board.getMineElement(3).getCard();
            } else if (c.getX() == 0) { // B2
                return this.board.getMineElement(2).getCard();
            } else if (c.getX() == -2) { // B1
                return this.board.getMineElement(1).getCard();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    // renvoie le numero du joueur courant
    public int currentNumPlayer() {
        return this.currentPlayer;
    }

    // renvoie l'objet Player du joueur courant
    public Player getCurrentPlayer() {
        return arrayPlayer.get(this.currentPlayer);
    }

    // renvoie une arrayList de tous les joueurs dans la partie
    public ArrayList<Player> getAllPlayers() {
        return this.arrayPlayer;
    }

    public boolean reinitSavedDeck(String[] handCards, DeckGalleryAction deck) {
        int x, y;
        String[] tempSplit;
        boolean north = false, east = false, south = false, west = false, center = false;
        String actionType;

        for (int j = 0; j < handCards.length; j++) {
            GalleryCard gallerycard = null;
            ActionCard actioncard = null;
            RepareSabotageCard repair = null, sabotage = null;
            String[] eachCard = handCards[j].split("[:]");

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

                gallerycard = new GalleryCard(Gallery_t.tunnel, x, y, center, north, south, east, west);
                deck.addCardToDeck(gallerycard);
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
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}")) {
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Pickaxe);
                        } else
                            return false;
                    } else if (tempSplit[0].equals("Repare")) {
                        actionType = tempSplit[0];
                        if (tempSplit[1].equals("Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            repair = new RepareSabotageCard("Repare", Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", Tools.Pickaxe);
                        else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Wagon);
                        else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Pickaxe);
                        else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", Tools.Pickaxe, Tools.Wagon);
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
                gallerycard = new GalleryCard(Gallery_t.tunnel, x, y, center, north, south, east, west);
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
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}")) {
                            sabotage = new RepareSabotageCard("Sabotage", Tools.Pickaxe);
                        } else
                            return false;
                    } else if (tempSplit[0].equals("Repare")) {
                        actionType = tempSplit[0];
                        if (tempSplit[1].equals("Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern);
                        else if (tempSplit[1].equals("Wagon}"))
                            repair = new RepareSabotageCard("Repare", Tools.Wagon);
                        else if (tempSplit[1].equals("Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", Tools.Pickaxe);
                        else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Wagon);
                        else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                            repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Pickaxe);
                        else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                            repair = new RepareSabotageCard("Repare", Tools.Pickaxe, Tools.Wagon);
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
                return false;
            }
            // System.out.println(player);
        }
        return true;
    }

    public void save(String filename) {
        try {
            PrintWriter saveFile = new PrintWriter(filename, "UTF-8");
            saveFile.println(this.arrayPlayer.size());
            for (int i = 0; i < arrayPlayer.size(); i++) {
                saveFile.println(arrayPlayer.get(i));
            }
            saveFile.println(this.arrayPlayer.get(currentPlayer).getPlayerName());
            saveFile.println(this.pile.nbCard());
            saveFile.println(this.pile); // les cartes dans la pioche
            for (int i = 0; i < this.board.getMineSize(); i++) {
                saveFile.print(this.board.getMineElement(i).saveToFile());
                if (i < this.board.getMineSize() - 1)
                    saveFile.print("?");
            }
            saveFile.close();
        } catch (IOException e) {
            System.err.println("Erreur: Echec d'ouverture du fichier.");
        }
    }

    public boolean load(String filename) {
        try {
            FileReader filereader = new FileReader(filename);
            BufferedReader br = new BufferedReader(filereader);
            String playerName, playerType, playerRole, tempGoldPoints, attr, temp;
            int goldPoints;
            // lecture du nombre de joueurs
            temp = br.readLine();
            if (!temp.matches("([3-9])|(10)"))
                return false;
            int nbPlayers = Integer.parseInt(temp);
            Player player;
            int mineurs = 0, saboteurs = 0;

            // création des joueurs enregistrés
            for (int i = 0; i < nbPlayers; i++) {
                playerName = br.readLine();
                playerType = br.readLine();
                if (playerType.equals("Humain")) {

                    player = new PlayerHuman(playerName);

                } else if (playerType.equals("IA")) {
                    temp = br.readLine();
                    if (temp.equals("Easy"))
                        player = new IA(playerName, Difficulty.Easy);
                    else if (temp.equals("Medium"))
                        player = new IA(playerName, Difficulty.Medium);
                    else if (temp.equals("Hard"))
                        player = new IA(playerName, Difficulty.Hard);
                    else
                        return false;
                } else
                    return false;

                playerRole = br.readLine();
                Card rolecard;

                if (playerRole.equals("Mineur")) {
                    rolecard = new RoleCard("Mineur");
                    mineurs++;
                } else if (playerRole.equals("Saboteur")) {
                    rolecard = new RoleCard("Saboteur");
                    saboteurs++;
                } else
                    return false;

                player.assignRole(rolecard);
                tempGoldPoints = br.readLine(); // System.out.println(player.getRole());
                if (tempGoldPoints.matches("[0-9]+")) {
                    goldPoints = Integer.parseInt(tempGoldPoints);
                    player.setGoldPoints(goldPoints);
                } else
                    return false;

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
                            player.putSabotage(new RepareSabotageCard("Sabotage", Tools.Lantern), player);
                        } else if (kCard[1].equals("Sabotage{Wagon}"))
                            player.putSabotage(new RepareSabotageCard("Sabotage", Tools.Wagon), player);
                        else if (kCard[1].equals("Sabotage{Pickaxe}"))
                            player.putSabotage(new RepareSabotageCard("Sabotage", Tools.Pickaxe), player);
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

                if (!reinitSavedPlayerHand(handCards, player))
                    return false;
                this.arrayPlayer.add(player);

                temp = br.readLine();
            }

            roleCards.fillHand(saboteurs, mineurs);

            // setting the currentplayer
            temp = br.readLine();
            int foundplayer = 0;
            for (int i = 0; i < this.nbPlayer(); i++) {
                if (this.arrayPlayer.get(i).getPlayerName().equals(temp)) {
                    foundplayer++;
                    this.currentPlayer = i;
                    break;
                }
            }
            if (foundplayer == 0)
                return false;

            // to get the number of cards remaining in the Deck
            temp = br.readLine();
            int deckSize;
            if (!temp.matches("[0-9]+"))
                return false;
            deckSize = Integer.parseInt(temp);
            if (deckSize > 67)
                return false;

            // setting the Deck

            temp = br.readLine();
            String[] savedDeck = temp.split("[;]");
            DeckGalleryAction tempDeck = new DeckGalleryAction(deckSize);

            boolean goodDeck = true;
            if (!reinitSavedDeck(savedDeck, tempDeck)) {
                goodDeck = false;
            }

            if (goodDeck)
                this.pile = tempDeck; // System.out.println("Good deck");
            else
                return false;

            // setting the mine
            temp = br.readLine();
            //System.out.println(temp);
            String[] mineCards = temp.split("[?]");

            if (!reinitBoard(mineCards))
                return false;

            br.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean reinitBoard(String[] boardCards) {
        int north = 0, south = 0, east = 0, west = 0;
        String[] indexes;
        GalleryCard gc;
        Node node = null;
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
                    int x = gc.getX(), y = gc.getY();
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
            gallerycard = new GalleryCard(Gallery_t.tunnel, x, y, center, north, south, east, west);
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
                        sabotage = new RepareSabotageCard("Sabotage", Tools.Lantern);
                    else if (tempSplit[1].equals("Wagon}"))
                        sabotage = new RepareSabotageCard("Sabotage", Tools.Wagon);
                    else if (tempSplit[1].equals("Pickaxe}")) {
                        sabotage = new RepareSabotageCard("Sabotage", Tools.Pickaxe);
                    } else
                        throw new Exception("Incorrect tool format for an ActionCard!");
                } else if (tempSplit[0].equals("Repare")) {
                    actionType = tempSplit[0];
                    if (tempSplit[1].equals("Lantern}"))
                        repair = new RepareSabotageCard("Repare", Tools.Lantern);
                    else if (tempSplit[1].equals("Wagon}"))
                        repair = new RepareSabotageCard("Repare", Tools.Wagon);
                    else if (tempSplit[1].equals("Pickaxe}"))
                        repair = new RepareSabotageCard("Repare", Tools.Pickaxe);
                    else if (tempSplit[1].equals("Lantern,Wagon}") || tempSplit[1].equals("Wagon,Lantern}"))
                        repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Wagon);
                    else if (tempSplit[1].equals("Lantern,Pickaxe}") || tempSplit[1].equals("Pickaxe,Lantern}"))
                        repair = new RepareSabotageCard("Repare", Tools.Lantern, Tools.Pickaxe);
                    else if (tempSplit[1].equals("Pickaxe,Wagon}") || tempSplit[1].equals("Wagon,Pickaxe}"))
                        repair = new RepareSabotageCard("Repare", Tools.Pickaxe, Tools.Wagon);
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

    public String toString() {
        String renvoi = "Moteur: \n";

        renvoi += "Joueur courant: " + this.getCurrentPlayer().getPlayerName() + "\n";
        renvoi += "Deck: " + this.pile.nbCard() + " cartes \n";
        renvoi += this.roleCards.print_without_visibility() + "\n";
        renvoi += "Joueurs [ ";
        for (int i = 0; i < nbPlayer(); i++) {
            renvoi += this.getPlayer(i).getPlayerName() + " ; ";
        }
        renvoi += "]\n";

        return renvoi;
    }

}
