package com.xingray.project.generator.core.maven;

import com.xingray.java.maven.core.model.Plugin;
import com.xingray.java.maven.core.model.ProjectObjectModel;
import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.generator.BuildSystemFileGenerator;

import java.io.File;

public class MavenBuildSystemFileGenerator implements BuildSystemFileGenerator {

    private final XmlConverter xmlConverter;

    public MavenBuildSystemFileGenerator(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }

    @Override
    public FileTreeNode generatorBuildSystemFile(Project project) {
        File projectRootFile = new File(project.getLocation(), project.getName());
        File pomFile = new File(projectRootFile, "pom.xml");

        ProjectObjectModel pom = ProjectObjectModel.ofGav(project.getGav());
        pom.addProperty("maven.compiler.source", "19")
                .addProperty("maven.compiler.target", "19")
                .addProperty("project.build.sourceEncoding", "UTF-8");

        pom.build().addPlugin(Plugin.ofGav("org.apache.maven.plugins:maven-jar-plugin:3.3.0")
                .configuration()
                .addAndGetChild("archive")
                .addAndGetChild("manifest")
                .put("addClasspath", true)
                .put("mainClass", project.getMainClass())
                .plugin());

        String xml = xmlConverter.objectToXml(pom);

        return FileTreeNode.createNode(pomFile, xml);
    }
}
