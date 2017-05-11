package Saboteur;


import java.util.ArrayList;
import Player.*;

public class Lobby {

    ArrayList<Player> arrayPlayer;

    Lobby(){
        arrayPlayer = new ArrayList<>();
    }

    // ajoute un joueur dans le lobby
    public void addPlayer(String name, String type){
        if(type.equals("Ordinateur")){
            arrayPlayer.add(new IA(name));
        } else if(type.equals("Joueur")){
            arrayPlayer.add(new PlayerHuman(name));
        }
    }

    // enlève un joueur du lobby
    public void deletePlayer(int i){
        if(i >= 0 && i < nbPlayer()){
            arrayPlayer.remove(i);
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
        return nbPlayer() >= 10;
    }


    // renvoie le tableau pour le moteur par exemple
    public ArrayList<Player> getArrayPlayer(){
        return this.arrayPlayer;
    }


}
