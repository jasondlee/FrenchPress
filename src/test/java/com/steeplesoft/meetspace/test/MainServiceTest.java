/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.meetspace.test;

import org.junit.Test;
import com.steeplesoft.frenchpress.model.Sponsor;
import com.steeplesoft.frenchpress.service.MainService;
import javax.inject.Inject;
import static org.junit.Assert.*;

//@RunWith(Arquillian.class)
public class MainServiceTest {
    @Inject
    MainService mainService;

//    @Deployment
//    public static Archive<?> createTestArchive() {
//        return ShrinkWrap.create("test.jar", JavaArchive.class)
//                .addClasses(MainService.class, MainServiceImpl.class, GroupMember.class,
//                BlogEntry.class, Registration.class, Sponsor.class);
//    }

//    @Test
    public void testgetRandomSponsor() {
        Sponsor sponsor = mainService.getRandomSponsor();
        assertNotNull(sponsor);
    }
    @Test
    public void dummy() {
        
    }
}