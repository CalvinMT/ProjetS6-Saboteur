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
		Player p = new Player(playerName);
		assertTrue(p != null);
	}

	/**
	 * Test method for {@link saboteur.Player#getPlayerName()}.
	 */
	@Test
	public void testGetPlayerName() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getPlayerName() == "Ada");
	}
	
	/**
	 * Test method for {@link saboteur.Player#assignRole()}.
	 */
	@Test
	public void testAssignRole() {
		String playerName = "Ada";
		Card c = new Card(player);
		Player p = new Player(playerName);
		p.assignRole(c);
		assertTrue(p.role.getType() == player);
	}

	/**
	 * Test method for {@link saboteur.Player#getRole()}.
	 */
	@Test
	public void testGetRole() {
		String playerName = "Ada";
		Card role = new Card(action);
		Player p = new Player(playerName);
		p.assignRole(role);
		Card c = p.getRole();
		assertTrue(c.getType() == action);
	}

	/**
	 * Test method for {@link saboteur.Player#getGoldPoints()}.
	 */
	@Test
	public void testGetGoldPoints() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getGoldPoints() == 0);
	}

	/**
	 * Test method for {@link saboteur.Player#getPlayableCards()}.
	 */
	@Test
	public void testGetPlayableCards() {
		String playerName = "Ada";
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(player);
		startCards[3] = new Card(action);
		Player p = new Player(playerName);
		p.assignPlayingCards(startCards); 
		assertTrue(p.playableCards.length == startCards.length);
	}

	/**
	 * Test method for {@link saboteur.Player#getPauseCards()}.
	 */
	@Test
	public void testGetPauseCards() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.pauseCards.length == 3);
	}

	/**
	 * Test method for {@link saboteur.Player#getTreasureCardsChecked()}.
	 */
	@Test
	public void testGetTreasureCardsChecked() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.treasureCardsChecked.length == 3);
	}

	/**
	 * Test method for {@link saboteur.Player#changeACard(Cards.Card, int)}.
	 */
	@Test
	public void testChangeACard() {
		String playerName = "Ada";
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(player);
		startCards[3] = new Card(action);
		Player p = new Player(playerName);
		p.assignPlayingCards(startCards);
		assertTrue(p.playableCards[0].getType() == action);
		assertTrue(p.playableCards[1].getType() == gallery);
		assertTrue(p.playableCards[2].getType() == player);
		assertTrue(p.playableCards[3].getType() == action);
		Card newCard = new Card(gallery);
		p.changeACard(newCard, 3);
		assertTrue(p.playableCards[3].getType() == gallery);
	}
	
	/**
	 * Test method for {@link saboteur.Player#assignPlayingCards(Card[] c)}.
	 */
	@Test
	public void testAssignPlayingCards() {
		String playerName = "Ada";
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(player);
		startCards[3] = new Card(action);
		Player p = new Player(playerName);
		p.assignPlayingCards(startCards);
		assertTrue(p.playableCards[0].getType() == action);
		assertTrue(p.playableCards[1].getType() == gallery);
		assertTrue(p.playableCards[2].getType() == player);
		assertTrue(p.playableCards[3].getType() == action);
	}

}
