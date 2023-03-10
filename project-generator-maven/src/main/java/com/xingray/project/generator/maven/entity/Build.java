package com.xingray.project.generator.maven.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

public class Build {
    //    @JacksonXmlProperty(localName = "plugin")
    @JacksonXmlElementWrapper(localName = "plugins")
    @JacksonXmlProperty(localName = "plugin")
    private List<Plugin> plugins;

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    @Override
    public String toString() {
        return "Build{" +
                "plugins=" + plugins +
                '}';
    }
}
