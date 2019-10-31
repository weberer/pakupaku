package test;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.hamcrest.CoreMatchers;
import Controller.GameController;
import Model.GameData;
import Model.Paku;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class PakuTest
{
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Paku.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void move()
    {
        GameData gameData = GameData.getInstance();
       // ArrayList row = map.get(loc.getyLoc());
        Paku paku = Paku.getInstance();
        paku.move();

    }

    @Test
    public void substractLife()
    {
        Paku paku = Paku.getInstance();
        int lives = paku.getRemainingLife();
        paku.substractLife();
        int newLives = paku.getRemainingLife();
        Assert.assertEquals(lives - 1, newLives);
    }

    @Test
    public void addLife()
    {
        Paku paku = Paku.getInstance();
        int lives = paku.getRemainingLife();
        paku.addLife();
        int newLives = paku.getRemainingLife();
        Assert.assertEquals(lives + 1, newLives);
    }

    @Test
    public void resetPaku()
    {
        Paku paku = Paku.getInstance();
        GameData gameData = GameData.getInstance();
        new GameController();
        paku.setGameData(gameData);
        paku.setMap(gameData.getMap());
        paku.move();
        paku.resetPaku();
        Assert.assertEquals(paku.getLoc().getxLoc(), paku.getSTARTING_X());
        Assert.assertEquals(paku.getLoc().getyLoc(), paku.getSTARTING_Y());
    }

    @Test
    public void resetLocation() {
    }
}
