package Saboteur;
import Player.Player.Difficulty;


import java.util.ArrayList;
import Player.*;

public class Lobby {

    ArrayList<Player> arrayPlayer;

    public Lobby(){
        arrayPlayer = new ArrayList<>();
    }

    // ajoute un joueur dans le lobby
    public void addPlayer(int num, String name, String avatar){
        if(arrayPlayer.size() < 10){
            arrayPlayer.add(new PlayerHuman(num, name, avatar));
        }
    }

    // ajoute une IA dans le lobby
    public void addPlayer(int num, String name, Difficulty d){
        if(arrayPlayer.size() < 10){
            Player p = new IA(num, name, d);
            arrayPlayer.add(p);
        }
    }

    // enlève un joueur du lobby
    public void deletePlayer(int num){
        if(num >= 0 && num < nbPlayer() && arrayPlayer.size() != 0){
            arrayPlayer.remove(num);
        }

        Player p;
        String name;
        int pnum;

        // on décrémente le num de chacun
        for(int i=num; i<nbPlayer(); i++){
            p = arrayPlayer.get(i);
            pnum = p.getNum();
            p.setNum(pnum-1);


            // on change le nom du joueur
            name = p.getPlayerName();

            if(p.getDifficulty() == Difficulty.Easy || p.getDifficulty() == Difficulty.Medium || p.getDifficulty() == Difficulty.Hard){
                p.setPlayerName("IA "+(pnum));
            } else if(name.equals("Joueur "+(pnum+1))){
                System.out.println("Joueur "+(pnum));
                p.setPlayerName("Joueur "+(pnum));
            }

        }
    }

    // renvoie le nombre de joueur
    public int nbPlayer(){
        return arrayPlayer.size();
    }

    // s'il y a assez de joueur dans le lobby
    public boolean enoughPlayer(){
        return nbPlayer() >= 3;
    }

    // si on peut encore ajouter des joueurs
    public boolean tooMuchPlayer(){
        return nbPlayer() > 10;
    }


    // renvoie le tableau pour le moteur par exemple
    public ArrayList<Player> getArrayPlayer(){
        return this.arrayPlayer;
    }

    // met a jour la difficulté de l'IA
    public void updateDifficulty(int num, Difficulty d){
        arrayPlayer.get(num).setDifficulty(d);
    }


    public String toString(){
        String renvoi = "";

        renvoi += "Nombre joueur: "+nbPlayer()+"\n";
        renvoi += "{ ";
        for(int i=0; i<nbPlayer(); i++){
            renvoi += arrayPlayer.get(i).getNum()+ ": "+ arrayPlayer.get(i).getPlayerName() + " Difficulté: "+this.arrayPlayer.get(i).getDifficulty() + " ; ";
//            renvoi += arrayPlayer.get(i);
        }

        renvoi += "}\n";

        return renvoi;
    }
}
