/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package saboteur;
import Cards.*;

/**
 *
 * @author uwalakae
 */
public class Moteur {
    // attributs
    
    // constructeur
    
    
    
    // ajoute une carte qui va amener au "trésor potentiel" sur la gallerie
    public void towardsTreasure(Joueur player, Card cardToPlay, Card cardToAppend) {
        
    }
    
    // enleve une carte qui empeche d'arriver au(x) but(s)
    // serviable pour un joueur qui est un miner
    public void destroyCulDeSac(Joueur player, Card cardToPlay) {
        
    }
    
    // enleve une carte de pause correspondant au type de Carte c devant le Joueur j
    // pourrait meme utiliser pour lui meme
    public void healPlayer(Joueur player, Card cardToPlay, Joueur woundedPlayer) {
        
    }
    
    public void sabotagePlayer(Joueur player, Card cardToPlay, Joueur targetedPlayer) {
        
    }
    
    public void peepCard(Joueur player, Card cardToPlay) {
        
    }
    
    public void shareGoldAmongMiners(Joueur [] players){
        
    }
    
    public void shareGoldAmongSaboteurs(Joueur [] players){
        
    }
}
