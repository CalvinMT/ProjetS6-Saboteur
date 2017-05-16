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

            arrayPlayer.add(new IA(num, name));
        }
    }

    // enlève un joueur du lobby
    public void deletePlayer(int num){
        if(num >= 0 && num < nbPlayer() && arrayPlayer.size() != 0){
            arrayPlayer.remove(num);
        }

        // on décrémente le num de chacun
        for(int i=num; i<nbPlayer(); i++){
            num = arrayPlayer.get(i).getNum();
            arrayPlayer.get(i).setNum(num-1);
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
            renvoi += arrayPlayer.get(i).getNum()+ ": "+ arrayPlayer.get(i).getPlayerName() + " ; ";
        }

        renvoi += "}\n";

        return renvoi;
    }
}
