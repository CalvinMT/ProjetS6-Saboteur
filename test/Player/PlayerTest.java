/**
 * 
 */
package Player;

import static org.junit.Assert.*;
import static Cards.Card.Card_t.*;

import Cards.*;
import Player.Player;
import Player.Player.Difficulty;
import org.junit.Assert;
import org.junit.Test;
import Board.Board;


public class PlayerTest {


	@Test
	public void testPlayer() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);
		assertTrue(p != null);
	}


	@Test
	public void testPlayer2(){
		Player p = new PlayerHuman(1, "Maxime", "mdr");
		Assert.assertTrue(p.getDifficulty() == Difficulty.Player);
	}

	@Test
	public void testPlayer3(){
		Player p = new IA(1, "Bot", Difficulty.Easy);
		System.out.println(p.getDifficulty());
		Assert.assertTrue(p.getDifficulty() == Difficulty.Easy);
		Assert.assertTrue(p.getAvatar() == "robot_miner");
	}

	@Test
	public void setDiff1(){
		Player p = new IA(1, "Bot", Difficulty.Easy);
		p.setDifficulty(Difficulty.Hard);
		Assert.assertTrue(p.getDifficulty() == Difficulty.Hard);
	}

	@Test
	public void testGetPlayerName() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);
		assertTrue(p.getPlayerName() == "Ada");
	}

	@Test
	public void testAssignRole() {
		Board b = new Board();
		String playerName = "Ada";
		Card c = new RoleCard("Mineur");
		Player p = new PlayerHuman(0, playerName, b);
		p.assignRole(c);
		assertTrue(p.getRole().getType() == role);
	}

	@Test
	public void testGetRole() {
		Board b = new Board();
		String playerName = "Ada";
		Card role = new ActionCard("Map");
		Player p = new PlayerHuman(0, playerName, b);
		p.assignRole(role);

//		Card c = p.getRole();
		Assert.assertTrue(p.getRole() == null);
	}

	@Test
	public void testGetGoldPoints() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);
		assertTrue(p.getGoldPoints() >= 0);
	}

	@Test
	public void testGetPlayableCards() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);
		assertTrue(p.nbCardHand() >= 0);
	}

	@Test
	public void testGetPauseCards() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);
		assertTrue(p.getAttributeCards().nbCard() == 0);
	}

	@Test
	public void testHandCard() {
		Board b = new Board();
		String playerName = "Ada";
		Player p = new PlayerHuman(0, playerName, b);

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
