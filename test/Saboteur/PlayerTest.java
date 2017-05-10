/**
 * 
 */
package Saboteur;

import static org.junit.Assert.*;
import static Cards.Card.Card_t.*;

import Cards.*;
import Player.Player;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author uwalakae
 *
 */
public class PlayerTest {


	@Test
	public void testPlayer() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p != null);
	}

	@Test
	public void testGetPlayerName() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getPlayerName() == "Ada");
	}

	@Test
	public void testAssignRole() {
		String playerName = "Ada";
		Card c = new Card(role);
		Player p = new Player(playerName);
		p.assignRole(c);
		assertTrue(p.getRole().getType() == role);
	}

	@Test
	public void testGetRole() {
		String playerName = "Ada";
		Card role = new ActionCard("Map");
		Player p = new Player(playerName);
		p.assignRole(role);

//		Card c = p.getRole();
		Assert.assertTrue(p.getRole() == null);
	}

	@Test
	public void testGetGoldPoints() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getGoldPoints() >= 0);
	}

	@Test
	public void testGetPlayableCards() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.nbCardHand() >= 0);
	}

	@Test
	public void testGetPauseCards() {
		String playerName = "Ada";
		Player p = new Player(playerName);
		assertTrue(p.getAttributeCards().nbCard() == 0);
	}

	@Test
	public void testHandCard() {
		String playerName = "Ada";
		Player p = new Player(playerName);

		p.drawCard(new ActionCard("Map"));
		p.drawCard(new ActionCard("Map"));
		p.drawCard(new GalleryCard(true, true, true, true, true));
		p.drawCard(new RoleCard("Mineur"));
		Assert.assertTrue(p.nbCardHand() == 3);

		p.discard(3);
		Assert.assertTrue(p.nbCardHand() == 3);

		p.discard(2);
		Assert.assertTrue(p.nbCardHand() == 2);
	}


}
