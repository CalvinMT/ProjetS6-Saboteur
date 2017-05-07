/**
 * 
 */
package Saboteur;

import static org.junit.Assert.*;
import static Cards.Card.Card_t.*;

import Cards.ActionCard;
import org.junit.Assert;
import org.junit.Test;

import Cards.Card;

/**
 * @author uwalakae
 *
 */
public class PlayerTest {

	/**
	 * Test method for {@link Saboteur.Player#(java.lang.String, Cards.Card, Cards.Card[])}.
	 */
	@Test
	public void testPlayer() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p != null);
	}

	/**
	 * Test method for {@link Saboteur.Player#getPlayerName()}.
	 */
	@Test
	public void testGetPlayerName() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getPlayerName() == "Ada");
	}
	
	/**
	 * Test method for {@link Saboteur.Player#()}.
	 */
	@Test
	public void testAssignRole() {
		String playerName = "Ada";
		Card c = new Card(role);
		Player p = new Player(playerName);
		p.assignRole(c);
		assertTrue(p.getRole().getType() == role);
	}

	/**
	 * Test method for {@link Saboteur.Player#getRole()}.
	 */
	@Test
	public void testGetRole() {
		String playerName = "Ada";
		Card role = new ActionCard("Map");
		Player p = new Player(playerName);
		p.assignRole(role);

//		Card c = p.getRole();
		Assert.assertTrue(p.getRole() == null);
	}

	/**
	 * Test method for {@link Saboteur.Player#getGoldPoints()}.
	 */
	@Test
	public void testGetGoldPoints() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getGoldPoints() == 0);
	}

	/**
	 * Test method for {@link Saboteur.Player#getPlayableCards()}.
	 */
	@Test
	public void testGetPlayableCards() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.nbCardHand() >= 0);
	}

	/**
	 * Test method for {@link Saboteur.Player#getPauseCards()}.
	 */
	@Test
	public void testGetPauseCards() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.pauseCards.length == 3);
	}

	/**
	 * Test method for {@link Saboteur.Player#()}.
	 */
	/*@Test
	public void testGetTreasureCardsChecked() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.treasureCardsChecked.length == 3);
	}*/

	/**
	 * Test method for {@link Saboteur.Player#(Cards.Card, int)}.
	 */
	/*@Test
	public void testChangeACard() {
		String playerName = "Ada";
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(role);
		startCards[3] = new Card(action);
		Player p = new Player(playerName);
		p.assignPlayingCards(startCards);
		assertTrue(p.playableCards[0].getType() == action);
		assertTrue(p.playableCards[1].getType() == gallery);
		assertTrue(p.playableCards[2].getType() == role);
		assertTrue(p.playableCards[3].getType() == action);
		Card newCard = new Card(gallery);
		p.changeACard(newCard, 3);
		assertTrue(p.playableCards[3].getType() == gallery);
	}*/
	
	/**
	 * Test method for {@link Saboteur.Player#assignPlayingCards(Card[] c)}.
	 */
	/*@Test
	public void testAssignPlayingCards() {
		String playerName = "Ada";
		Card[] startCards = new Card[4];
		startCards[0] = new Card(action);
		startCards[1] = new Card(gallery);
		startCards[2] = new Card(role);
		startCards[3] = new Card(action);
		Player p = new Player(playerName);
		p.assignPlayingCards(startCards);
		assertTrue(p.playableCards[0].getType() == action);
		assertTrue(p.playableCards[1].getType() == gallery);
		assertTrue(p.playableCards[2].getType() == role);
		assertTrue(p.playableCards[3].getType() == action);
	}*/

}
