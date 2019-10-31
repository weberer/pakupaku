package Model;

//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.hamcrest.CoreMatchers;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.Assert;

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
    public void addLife() {
    }

    @Test
    public void resetPaku() {
    }

    @Test
    public void resetLocation() {
    }
}
