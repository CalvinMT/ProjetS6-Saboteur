
package Cards;


// carte d'or pour la fin de la manche

public class GoldCard extends Card {
	private int type;
	

	public GoldCard(int type) {
		if (type == 1 || type == 2 || type == 3)
			this.type = type;
		else
			System.err.println("Valeur donné incorrecte!");
	}

	public int getGold(){
		return this.type;
	}
}
