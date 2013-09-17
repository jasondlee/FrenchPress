package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.Post;
import com.steeplesoft.frenchpress.rest.providers.FpEntityCollectionReaderWriter;
import com.steeplesoft.frenchpress.rest.providers.FpEntityReaderWriter;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.FileAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by jdlee on 9/16/13.
 */
@RunWith(Arquillian.class)
@RunAsClient
@Category(IntegrationTests.class)
public class PostRestTest extends Arquillian {
    @ArquillianResource
    private java.net.URL url;

    public PostRestTest() throws InitializationError {
        super(PostRestTest.class);
    }

    @Deployment
    public static WebArchive createTestArchive() {
        return ShrinkWrap.create(WebArchive.class)
                .addPackages(true, "com.steeplesoft.frenchpress")
                .addPackages(true, "org.codehaus")
                .addPackages(true, "com.google.gson")
                .addAsResource("persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(new FileAsset(new File("src/main/webapp/WEB-INF/web.xml")), "web.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void crudViaRest() throws URISyntaxException {
        assertNotNull(url);

        Client client = ClientBuilder.newClient()
                .register(new FpEntityCollectionReaderWriter())
                .register(new FpEntityReaderWriter());
        WebTarget target = client.target(url.toURI()).path("rest").path("posts");

        // Create Post
        Response response = target.request().accept(MediaType.APPLICATION_JSON).get();
        assertNotNull(response);
        assertNotNull(response.readEntity(String.class));
        Post post = new Post();
        post.setTitle("REST Test");
        post.setSlug("rest-test");

        response = target.request(MediaType.APPLICATION_JSON).post(Entity.entity(post, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        Post newPost = response.readEntity(Post.class);
        assertNotNull(newPost);

        // Update Post
        newPost.setTitle("Updated");
        response = target.path("id").path(newPost.getId().toString()).request().put(Entity.entity(newPost, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        // Delete Post
        response = target.path("id").path(newPost.getId().toString()).request().delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
    }
}
