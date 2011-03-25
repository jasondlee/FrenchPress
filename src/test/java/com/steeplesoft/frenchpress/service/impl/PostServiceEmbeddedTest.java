package com.steeplesoft.frenchpress.service.impl;

import java.io.File;
import java.io.IOException;

import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;
import org.junit.Test;


/**
 *
 * @author jasonlee
 */
public class PostServiceEmbeddedTest {
    private static GlassFish glassfish;
    
//    @BeforeClass
    public static void beforeClass() throws GlassFishException, IOException {
        GlassFishProperties gfProps = new GlassFishProperties();
        
        gfProps.setInstanceRoot("src/test/glassfish");

        glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProps);
        glassfish.start();

        org.glassfish.embeddable.Deployer deployer = glassfish.getDeployer();

        // Create a scattered web application.
        ScatteredArchive archive = new ScatteredArchive("testapp", ScatteredArchive.Type.WAR);
        archive.addClassPath(new File("target", "test-classes"));
        archive.addClassPath(new File("target", "classes"));
        archive.addClassPath(new File("src/main/", "webapp"));

        deployer.deploy(archive.toURI());
    }
    
//    @AfterClass
    public static void afterClass() throws GlassFishException {
        // you can start it again.
        glassfish.stop();
        // you can not start it again. But you can embed a fresh glassfish with GlassFishRuntime.bootstrap().newGlassFish()
        glassfish.dispose();
    }
    
    @Test
    public void dummy() {
        
    }
}