package Board;


import Cards.GalleryCard;

import java.util.Random;

import static Cards.GalleryCard.Gallery_t.but;

public class Board {
    Mine mine; // Les cartes possées + start
    Cards.GalleryCard []goals; // Les trois buts dont 1 avec minerai

    Board() {
        Random r = new Random();
        int gold = r.nextInt(3) - 1; // Décide du but avec minerai
        int x;

        for (int i = -1; i < 2; i++) {
            x = 2*i;
            if (i == gold) {
                goals[i] = new GalleryCard(but, x, 8, true, true, true, true ,true, true); // Minerai
            }
            else {
                if (r.nextInt(2) == 1) {
                    goals[i] = new GalleryCard(but, x, 8, false, true, false, true, true, false); // Sans minerai droit
                }
                else {
                    goals[i] = new GalleryCard(but, x, 8, false, true, false, true, false, true); // Sans minerai gauche
                }
            }
        }
        mine = new Mine(); // Initialise une nouvelle mine (uniquement carte start)
    }
}
