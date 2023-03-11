package com.xingray.project.generator.maven.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;
import java.util.Properties;

@JacksonXmlRootElement(localName = "project")
@JsonIgnoreProperties(ignoreUnknown = true, value = {"schemaLocation"})
@JsonPropertyOrder({"modelVersion", "groupId", "artifactId", "version", "properties", "dependencyManagement", "dependencies", "build"})
public class ProjectObjectModel {

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns")
    private String xmlns = "http://maven.apache.org/POM/4.0.0";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
    private String xsi = "http://www.w3.org/2001/XMLSchema-instance";

    @JacksonXmlProperty(isAttribute = true, localName = "xsi:schemaLocation")
    private String schemaLocation = "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd";

    private String modelVersion = "4.0.0";

    private String groupId;
    private String artifactId;
    private String version;

    private String packaging;
    private String name;
    private String description;
    private String url;

    @JacksonXmlElementWrapper(localName = "models")
    @JacksonXmlProperty(localName = "model")
    private List<String> modules;

    @JacksonXmlElementWrapper(localName = "dependencies")
    @JacksonXmlProperty(localName = "dependency")
    private List<Dependency> dependencies;
    private DependencyManagement dependencyManagement;

    private Properties properties;

    private Build build;

    public String getXmlns() {
        return xmlns;
    }

    public void setXmlns(String xmlns) {
        this.xmlns = xmlns;
    }

    public String getXsi() {
        return xsi;
    }

    public void setXsi(String xsi) {
        this.xsi = xsi;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public void setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
    }

    public String getModelVersion() {
        return modelVersion;
    }

    public void setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPackaging() {
        return packaging;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getModules() {
        return modules;
    }

    public void setModules(List<String> modules) {
        this.modules = modules;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public DependencyManagement getDependencyManagement() {
        return dependencyManagement;
    }

    public void setDependencyManagement(DependencyManagement dependencyManagement) {
        this.dependencyManagement = dependencyManagement;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "ProjectObjectModel{" +
                "xmlns='" + xmlns + '\'' +
                ", xsi='" + xsi + '\'' +
                ", schemaLocation='" + schemaLocation + '\'' +
                ", modelVersion='" + modelVersion + '\'' +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", packaging='" + packaging + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", modules=" + modules +
                ", dependencies=" + dependencies +
                ", dependencyManagement=" + dependencyManagement +
                ", properties=" + properties +
                ", build=" + build +
                '}';
    }

    public ProjectObjectModel() {
    }

    public ProjectObjectModel(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public static ProjectObjectModel ofGav(String[] gav) {
        return new ProjectObjectModel(gav[0], gav[1], gav[2]);
    }

    public static ProjectObjectModel ofGav(String gav) {
        return ofGav(gav.split(":"));
    }

    public ProjectObjectModel addProperty(String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.put(key, value);
        return this;
    }

    public Build build() {
        if (build == null) {
            build = new Build();
        }
        return build;
    }
}
