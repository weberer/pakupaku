package Model;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GhostTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(Ghost.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void inJail() {
    }

    @Test
    public void jailMove() {
    }

    @Test
    public void checkWarp() {
    }

    @Test
    public void scatterMove() {
    }

    @Test
    public void fleeMove() {
    }

    @Test
    public void eatenMove() {
    }

    @Test
    public void calculateMove() {
    }

    @Test
    public void addScore() {
    }

    @Test
    public void resetMultiplier() {
    }

    @Test
    public void resetLocation() {
    }

    @Test
    public void recordLocation() {
    }
}
