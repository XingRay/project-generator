package com.xingray.project.generator.core.maven.plugins;

import com.xingray.project.generator.core.maven.entity.Plugin;

public class MavenJarPlugin extends Plugin {

    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public MavenJarPlugin(String mainClass) {
        setGroupId("org.apache.maven.plugins");
        setArtifactId("maven-jar-plugin");
        setVersion("3.3.0");

        configuration = new Configuration();
        configuration.archive = new Archive();
        configuration.archive.manifest = new Manifest();
        configuration.archive.manifest.addClasspath = true;
        configuration.archive.manifest.mainClass = mainClass;
    }

    @Override
    public String toString() {
        return "MavenJarPlugin{" +
                "configuration=" + configuration +
                "} " + super.toString();
    }

    public static class Configuration {
        private Archive archive;

        public Archive getArchive() {
            return archive;
        }

        public void setArchive(Archive archive) {
            this.archive = archive;
        }

        @Override
        public String toString() {
            return "Configuration{" +
                    "archive=" + archive +
                    '}';
        }
    }

    public static class Archive {
        private Manifest manifest;

        public Manifest getManifest() {
            return manifest;
        }

        public void setManifest(Manifest manifest) {
            this.manifest = manifest;
        }

        @Override
        public String toString() {
            return "Archive{" +
                    "manifest=" + manifest +
                    '}';
        }
    }

    public static class Manifest {
        private Boolean addClasspath;
        private String mainClass;

        public Boolean getAddClasspath() {
            return addClasspath;
        }

        public void setAddClasspath(Boolean addClasspath) {
            this.addClasspath = addClasspath;
        }

        public String getMainClass() {
            return mainClass;
        }

        public void setMainClass(String mainClass) {
            this.mainClass = mainClass;
        }

        @Override
        public String toString() {
            return "Manifest{" +
                    "addClasspath=" + addClasspath +
                    ", mainClass='" + mainClass + '\'' +
                    '}';
        }
    }
}
