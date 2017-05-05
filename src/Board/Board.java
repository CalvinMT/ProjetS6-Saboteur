package Board;


import Cards.GalleryCard;

import java.util.ArrayList;
import java.util.Random;

import static Cards.GalleryCard.Gallery_t.but;

public class Board {
    ArrayList<Node> mine;



    public Board() {
        Random r = new Random();
        int gold = r.nextInt(3) -1;
        int x;

        mine.add(new Node());

        for (int i = -1; i < 2; i++) {
            x = 2*i;
            if (i == gold) {
                mine.add(new GalleryCard(but, x, 8, true, true, true, true ,true, true)); // Minerai
            }
            else {
                if (r.nextInt(2) == 1) {
                    mine.add(new GalleryCard(but, x, 8, false, true, false, true, true, false)); // Sans minerai droit
                }
                else {
                    mine.add(new GalleryCard(but, x, 8, false, true, false, true, false, true)); // Sans minerai gauche
                }
            }
        }
    }

    // Debug
    public Board(GalleryCard start, GalleryCard but1, GalleryCard but2, GalleryCard but3) {
        mine.add(new Node(start));
        mine.add(new Node(but1));
        mine.add(new Node(but2));
        mine.add(new Node(but3));
    }

    // TODO
    // ajoute la carte card à la suite de mine
    public void addCard(GalleryCard card) {

    }

    // TODO
    // Parcours des nodes si indices liens l:
    //      l = idx -> l = -1
    //      l > idx -> l = l - 1
    public void removeCard(int idx) {

    }
}
