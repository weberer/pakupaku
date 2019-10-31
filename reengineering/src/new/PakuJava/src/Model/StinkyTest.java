package Model;

import Controller.GameController;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Arquillian.class)
public class StinkyTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Stinky.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void resetLocation() {
        GameData gameData = GameData.getInstance();
        GameController gc = new GameController();
        Stinky stinky = new Stinky(gameData.getMap());
        stinky.getLoc().setxLoc(1);
        stinky.getLoc().setyLoc(1);
        stinky.resetLocation();
        assertEquals(14, stinky.getLoc().getxLoc());
        assertEquals(11, stinky.getLoc().getyLoc());
    }

    @Test
    public void move() {
    }

    @Test
    public void recordLocation() {
    }
}
