package com.xingray.project.generator.core.maven;

import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.generator.BuildSystemFileGenerator;
import com.xingray.project.generator.core.maven.entity.Build;
import com.xingray.project.generator.core.maven.entity.Plugin;
import com.xingray.project.generator.core.maven.entity.ProjectObjectModel;
import com.xingray.project.generator.core.maven.plugins.MavenCompilerPlugin;
import com.xingray.project.generator.core.maven.plugins.MavenJarPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MavenBuildSystemFileGenerator implements BuildSystemFileGenerator {

    private final XmlConverter xmlConverter;

    public MavenBuildSystemFileGenerator(XmlConverter xmlConverter) {
        this.xmlConverter = xmlConverter;
    }

    @Override
    public FileTreeNode generatorBuildSystemFile(Project project) {
        File projectRootFile = new File(project.getLocation(), project.getName());
        File pomFile = new File(projectRootFile, "pom.xml");
        ProjectObjectModel pom = new ProjectObjectModel();
        pom.setGroupId(project.getGroupId());
        pom.setArtifactId(project.getArtifactId());
        pom.setVersion(project.getVersion());

        HashMap<String, String> properties = new HashMap<>();
        properties.put("maven.compiler.source", "19");
        properties.put("maven.compiler.target", "19");
        properties.put("project.build.sourceEncoding", "UTF-8");
        pom.setProperties(properties);

        Build build = new Build();
        pom.setBuild(build);

        List<Plugin> plugins = new ArrayList<>();
        build.setPlugins(plugins);

        MavenCompilerPlugin mavenCompilerPlugin = new MavenCompilerPlugin();
        plugins.add(mavenCompilerPlugin);

        MavenJarPlugin mavenJarPlugin = new MavenJarPlugin(project.getMainClass());
        plugins.add(mavenJarPlugin);

        String xml = xmlConverter.objectToXml(pom);

        return FileTreeNode.createNode(pomFile, xml);
    }
}
