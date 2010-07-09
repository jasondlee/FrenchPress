/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.steeplesoft.frenchpress.test;

import com.steeplesoft.jsf.facestester.metadata.FacesConfig;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author jasonlee
 */
public class StaticAnalysisTest {
    @Test
    public void validatePhaseListeners() throws IOException, ParserConfigurationException, SAXException {
        FacesConfig fc = new FacesConfig (new File("src/main/webapp/WEB-INF/faces-config.xml"));//new File ("src/test/resources/META-INF/faces-config-good.xml"));
        fc.validatePhaseListeners();
    }

}
