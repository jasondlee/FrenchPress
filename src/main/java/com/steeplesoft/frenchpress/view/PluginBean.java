package com.steeplesoft.frenchpress.view;

//import com.steeplesoft.meetspace.plugins.engine.JsfPluginEngine;
//import com.steeplesoft.meetspace.plugins.engine.ClassloaderPluginEngine;
//import com.steeplesoft.meetspace.plugins.PluginMetadata;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.File;
import java.io.IOException;
import java.util.List;


@ManagedBean
@ApplicationScoped
public class PluginBean {
    /*
    private ClassloaderPluginEngine pe;

    @ManagedProperty("#{meetspaceBean}")
    private MeetSpaceBean meetspaceBean;

    @PostConstruct
    public void init() throws IOException {
        pe = new ClassloaderPluginEngine(meetspaceBean.getHome() + File.separatorChar + "/plugins", "com.steeplesoft.meetspace.plugins");
    }

    public List<Object> getPlugins(String type) {
        return pe.getPlugins(type);
    }

    public Object getPlugin(String type, String name) {
        final List<Object> objectList = getPlugins(type);
        for (Object obj : objectList) {
            PluginMetadata pd = new PluginMetadata(obj);
            if (pd.getType().equals(type) && pd.getName().equals(name)) {
                return obj;
            }
        }

        return null;
    }
    */
}
