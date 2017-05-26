package Saboteur;

import Player.*;
import org.junit.Assert;
import org.junit.Test;
import Player.Player.Difficulty;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class MoteurTest {

    @Test
    public void testcons1(){
        Moteur m = new Moteur(2);
        Assert.assertTrue(m.nbPlayer() == 3 && m.nbPlayer() > 0 && m.nbPlayer() <= 10);
        Assert.assertTrue(m.currentNumPlayer() == 0);
        Assert.assertTrue(m.getCurrentPlayer().getRole() == null);
        Assert.assertTrue(m.nbPlayer() > 0);
        Assert.assertTrue(m.currentNumPlayer() == 0);
    }

    @Test
    public void testcons2(){
        Moteur m = new Moteur(4);
        Assert.assertTrue(m.nbPlayer() == 4 && m.nbPlayer() > 0 && m.nbPlayer() <= 10);
        Assert.assertTrue(m.currentNumPlayer() == 0);
        Assert.assertTrue(m.getCurrentPlayer().getRole() == null);
        Assert.assertTrue(m.nbPlayer() > 0);
        Assert.assertTrue(m.currentNumPlayer() == 0);
    }

    @Test
    public void testcons3(){
        ArrayList<Player> array = new ArrayList<Player>();
        array.add(new PlayerHuman(0));
        array.add(new PlayerHuman(1));
        array.add(new PlayerHuman(2));
        Moteur m = new Moteur(array, "");
        Assert.assertTrue(m.nbPlayer() == 3);
        Assert.assertTrue(m.currentNumPlayer() == 0);
    }

    @Test
    public void testcons4(){
        ArrayList<Player> array = new ArrayList<Player>();
        array.add(new PlayerHuman(0));
        array.add(new PlayerHuman(1));
        Moteur m = new Moteur(array, "");
        Assert.assertTrue(m.currentNumPlayer() == -1);
    }

    @Test
    public void testHandCard1(){
        Moteur m = new Moteur(10);
        Assert.assertTrue(m.maxHandCard() == 4);
        for(int i=0; i<m.nbPlayer(); i++){
            Assert.assertTrue(m.getPlayer(i).nbCardHand() == 4);
        }
    }

    @Test
    public void testHandCard2(){
        Moteur m = new Moteur(11);
        Assert.assertTrue(m.maxHandCard() == 6);
        for(int i=0; i<m.nbPlayer(); i++){
            Assert.assertTrue(m.getPlayer(i).nbCardHand() == 6);
        }
        Assert.assertTrue(m.nbPlayer() > 0);
    }

    @Test
    public void testHandCard3(){
        Moteur m = new Moteur(2);
        Assert.assertTrue(m.maxHandCard() == 6);
        for(int i=0; i<m.nbPlayer(); i++){
            Assert.assertTrue(m.getPlayer(i).nbCardHand() == 6);
        }
        Assert.assertTrue(m.nbPlayer() > 0);
    }

    @Test
    public void testHandCard4(){
        Moteur m = new Moteur(6);
        Assert.assertTrue(m.maxHandCard() == 5);
        for(int i=0; i<m.nbPlayer(); i++){
            Assert.assertTrue(m.getPlayer(i).nbCardHand() == 5);
        }

        Assert.assertTrue(m.nbPlayer() > 0);
    }


    @Test
    public void testSaveLoad1(){

        Load l = new Load("loadfile");

        Boolean success = l.load();
        System.out.println(l.loadString());

        Assert.assertTrue(success);

    }

    @Test
    public void testSaveLoad2(){

        ArrayList<Player> arrayPlayer = new ArrayList<Player>();
        arrayPlayer.add(new PlayerHuman(0, "Thespygeek"));
        arrayPlayer.add(new PlayerHuman(1, "DrZed"));
        arrayPlayer.add(new PlayerHuman(2, "Ekalkas"));
        arrayPlayer.add(new IA(3, "IA 1" ,Difficulty.Easy));
        arrayPlayer.add(new IA(4, "IA 1" ,Difficulty.Medium));

        Moteur engine = new Moteur(arrayPlayer, "");

        while(!engine.allRoleAreSet()){
            try {
                engine.chooseRole(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        engine.save("Test1");

        Load l = new Load("Test1");
        Assert.assertTrue(l.load());




    }

}