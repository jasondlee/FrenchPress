/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.test;

import com.steeplesoft.frenchpress.model.BlogEntry;
import com.steeplesoft.frenchpress.model.Meeting;
import com.steeplesoft.frenchpress.model.Preference;
import com.steeplesoft.frenchpress.model.Sponsor;
import com.steeplesoft.frenchpress.model.User;
import com.steeplesoft.jsf.facestester.FacesTester;
import com.steeplesoft.jsf.facestester.injection.InjectionManager;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.BeforeClass;

/**
 *
 * @author jasonlee
 */
public class FrenchPressTestBase {
    protected static EntityManager em;
    protected static FacesTester ft;

    @BeforeClass
    public static void setup() {
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("testPU");
        em = emFactory.createEntityManager();

        loadDefaultTestData();

        InjectionManager.registerObject("em", em);
        ft = new FacesTester();
    }

    protected static void loadDefaultTestData() {
        em.getTransaction().begin();

        loadPreferences();
        loadMeetings();
        loadSponsors();
        loadUsers();
        loadBlogEntries();
        em.getTransaction().commit();
    }

    protected static void loadPreferences() {
        em.persist(new Preference("theme", "okcjug"));
    }

    protected static void loadMeetings() {
        Random r = new Random();
        int maxRows = r.nextInt(10);
        Calendar today = new GregorianCalendar();
        Date startTime = new GregorianCalendar(1, 1, 1, 11, 30, 0).getTime();
        Date endTime = new GregorianCalendar(1, 1, 1, 12, 30, 0).getTime();
        for (int i = 0; i < maxRows; i++) {
            Meeting meeting = new Meeting();
            meeting.setTopic("Topic for meeting #" + i);
            meeting.setName("Meeting #" +i);
            meeting.setDescription("Some<br/>really<br/>long<br/>description.");
            meeting.setSpeaker("Test User Foo");
            meeting.setStartTime(startTime);
            meeting.setEndTime(endTime);
            meeting.setMeetingDate(today.getTime());
            System.out.println(meeting);
            em.persist(meeting);
        }
    }

    protected static void loadSponsors() {
        Random r = new Random();
        int maxRows = r.nextInt(10);
        for (int i = 0; i < maxRows; i++) {
            Sponsor sponsor = new Sponsor();
            sponsor.setName("Sponsor #" + i);
            sponsor.setContactPerson("Contact #" + i);
            sponsor.setHomePage("http://blogs.steeplesoft.com");
            sponsor.setEmail("foo@example.com");
            em.persist(sponsor);
        }
    }

    protected static void loadUsers() {
        Random r = new Random();
        int maxRows = r.nextInt(10);
        for (int i = 0; i < maxRows; i++) {
            em.persist(new User("Test", "User"+i));
        }
    }

    protected static void loadBlogEntries() {
        Random r = new Random();
        int maxRows = r.nextInt(100)+10; // Minimum of 10 entries
        final User user = new User("Test", "User");
        em.persist(user);
        for (int i = 0; i < maxRows; i++) {
            BlogEntry entry = new BlogEntry();
            entry.setTitle("Blog #" + i);
            entry.setBody("Blog<br/>body<br/>#" + i);
            entry.setPostedBy(em.find(User.class, 0L));
            em.persist(entry);
        }
    }
}