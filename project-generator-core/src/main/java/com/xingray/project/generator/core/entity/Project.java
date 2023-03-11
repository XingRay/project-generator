package com.xingray.project.generator.core.entity;

public class Project {
    private String name;
    private String location;
    private Language language;
    private BuildSystem buildSystem;
    private String groupId;
    private String artifactId;
    private String version;

    private String mainClass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public BuildSystem getBuildSystem() {
        return buildSystem;
    }

    public void setBuildSystem(BuildSystem buildSystem) {
        this.buildSystem = buildSystem;
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

    public String getMainClass() {
        return mainClass;
    }

    public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", language=" + language +
                ", buildSystem=" + buildSystem +
                ", groupId='" + groupId + '\'' +
                ", artifactId='" + artifactId + '\'' +
                ", version='" + version + '\'' +
                ", mainClass='" + mainClass + '\'' +
                '}';
    }

    public String[] getGav() {
        return new String[]{groupId, artifactId, version};
    }

    public Project() {
    }

    public Project(String groupId, String artifactId, String version) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
    }

    public static Project ofGav(String[] gav) {
        return new Project(gav[0], gav[1], gav[2]);
    }

    public static Project ofGav(String gav) {
        return ofGav(gav.split(":"));
    }
}
