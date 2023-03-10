package com.xingray.project.generator.core.maven.plugins;

import com.xingray.project.generator.core.maven.entity.Plugin;

public class MavenCompilerPlugin extends Plugin {
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public MavenCompilerPlugin() {
        setGroupId("org.apache.maven.plugins");
        setArtifactId("maven-compiler-plugin");
        setVersion("3.10.1");

        configuration = new Configuration();
        configuration.source = "${maven.compiler.source}";
        configuration.target = "${maven.compiler.target}";
    }

    @Override
    public String toString() {
        return "MavenCompilerPlugin{" +
                "configuration=" + configuration +
                "} " + super.toString();
    }

    public static class Configuration {
        private String source;
        private String target;

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        @Override
        public String toString() {
            return "configuration{" +
                    "source='" + source + '\'' +
                    ", target='" + target + '\'' +
                    '}';
        }
    }


}
