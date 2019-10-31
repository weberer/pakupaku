package Controller;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GameControllerTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(GameController.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void startGame() {
    }

    @Test
    public void spawnGhosts() {
    }

    @Test
    public void respawn() {
    }

    @Test
    public void resetGame() {
    }

    @Test
    public void update() {
    }

    @Test
    public void receivedUserInput() {
    }

    @Test
    public void uiInput() {
    }

    @Test
    public void getDataToSend() {
    }
}
