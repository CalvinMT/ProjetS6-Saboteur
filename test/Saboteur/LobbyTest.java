package Saboteur;

import Player.Player;
import org.junit.Assert;
import org.junit.Test;
import Player.Player.Difficulty;

import static org.junit.Assert.*;



public class LobbyTest {

    @Test
    public void TestCons1(){
        Lobby l = new Lobby();

        for(int i=0; i<9; i++){
            l.addPlayer(i, "lel", Player.Difficulty.Player);
        }
        Assert.assertTrue(l.nbPlayer() >= 3 && l.nbPlayer() <= 10);
        Assert.assertFalse(l.tooMuchPlayer());
        Assert.assertTrue(l.enoughPlayer());
    }

    @Test
    public void TestCons2(){
        Lobby l = new Lobby();

        for(int i=0; i<11; i++){
            l.addPlayer(i, "lel", Player.Difficulty.Player);
        }
        Assert.assertTrue(l.nbPlayer() >= 3 && l.nbPlayer() <= 10);
        Assert.assertFalse(l.tooMuchPlayer());
        Assert.assertTrue(l.enoughPlayer());
    }


    @Test
    public void TestDelete1(){
        Lobby l = new Lobby();

        for(int i=0; i<9; i++){
            l.deletePlayer(0);
        }
        Assert.assertTrue(l.nbPlayer() >= 0);
        Assert.assertFalse(l.tooMuchPlayer());
        Assert.assertFalse(l.enoughPlayer());
    }


    @Test
    public void TestchangeDifficulty1(){
        Lobby l = new Lobby();

        l.addPlayer(0, "Thespygeek", "Avatar.png");
        l.addPlayer(1, "IA2", Difficulty.Easy);
        l.addPlayer(2, "IA3", Difficulty.Hard);

        Assert.assertTrue(l.nbPlayer() == 3);
        l.updateDifficulty(1, Difficulty.Easy);
        Assert.assertTrue(l.getArrayPlayer().get(1).getDifficulty() == Difficulty.Easy);
    }

    @Test
    public void TestchangeDifficulty2(){
        Lobby l = new Lobby();

        l.addPlayer(0, "Thespygeek", "Avatar.png");
        l.addPlayer(1, "IA2", Difficulty.Easy);
        l.addPlayer(2, "IA3", Difficulty.Hard);

        Assert.assertTrue(l.nbPlayer() == 3);
        l.updateDifficulty(1, Difficulty.Hard);
        Assert.assertTrue(l.getArrayPlayer().get(1).getDifficulty() == Difficulty.Hard);
    }




}