/**
 * 
 */
package saboteur;

import static org.junit.Assert.*;
import static Cards.Card.Card_t.*;

import org.junit.Test;

import Cards.Card;

/**
 * @author uwalakae
 *
 */
public class PlayerTest {

	/**
	 * Test method for {@link saboteur.Player#Player(java.lang.String, Cards.Card, Cards.Card[])}.
	 */
	@Test
	public void testPlayer() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		assertTrue(p != null);
	}

	/**
	 * Test method for {@link saboteur.Player#getPlayerName()}.
	 */
	@Test
	public void testGetPlayerName() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.getPlayerName() == "Ada");
	}

	/**
	 * Test method for {@link saboteur.Player#getRole()}.
	 */
	@Test
	public void testGetRole() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		Card c = p.getRole();
		assertTrue(c.getType() == action);
	}

	/**
	 * Test method for {@link saboteur.Player#getGoldPoints()}.
	 */
	@Test
	public void testGetGoldPoints() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.getGoldPoints() == 0);
	}

	/**
	 * Test method for {@link saboteur.Player#getPlayableCards()}.
	 */
	@Test
	public void testGetPlayableCards() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(player);
		startCards[3] = new Card(action);
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.playableCards.length == startCards.length);
	}

	/**
	 * Test method for {@link saboteur.Player#getPauseCards()}.
	 */
	@Test
	public void testGetPauseCards() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.pauseCards.length == 3);
	}

	/**
	 * Test method for {@link saboteur.Player#getTreasureCardsChecked()}.
	 */
	@Test
	public void testGetTreasureCardsChecked() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[6];
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.treasureCardsChecked.length == 3);
	}

	/**
	 * Test method for {@link saboteur.Player#changeACard(Cards.Card, int)}.
	 */
	@Test
	public void testChangeACard() {
		String playerName = "Ada";
		Card role = new Card(action);
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(player);
		startCards[3] = new Card(action);
		Player p = new Player(playerName, role, startCards);
		assertTrue(p.playableCards[0].getType() == action);
		assertTrue(p.playableCards[1].getType() == gallery);
		assertTrue(p.playableCards[2].getType() == player);
		assertTrue(p.playableCards[3].getType() == action);
		Card newCard = new Card(gallery);
		p.changeACard(newCard, 3);
		assertTrue(p.playableCards[3].getType() == gallery);
	}

}
