package com.xingray.project.generator.core.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xingray.java.command.CommandExecutor;
import com.xingray.java.command.JavaRuntimeCommandExecutor;
import com.xingray.java.command.SimpleExecuteListener;
import com.xingray.project.generator.core.entity.BuildSystem;
import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Language;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.generator.BuildSystemFileGenerator;
import com.xingray.project.generator.core.generator.ProjectGenerator;
import com.xingray.project.generator.core.generator.impl.ProjectGeneratorImpl;
import com.xingray.project.generator.core.maven.JacksonXmlConverter;
import com.xingray.project.generator.core.maven.MavenBuildSystemFileGenerator;
import com.xingray.project.generator.core.maven.XmlConverter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class ProjectGeneratorTest {
    @Test
    public void helloWorldProjectGenerateTest() {
        Project project = new Project();
        project.setName("generator-test01");
        project.setLocation(new File("src/test/output").getAbsolutePath());
        project.setLanguage(Language.JAVA);
        project.setBuildSystem(BuildSystem.MAVEN);
        project.setGroupId("com.xingray");
        project.setArtifactId("generator-test01");
        project.setVersion("1.0.0");

        XmlMapper xmlMapper = XmlMapper.builder()
                .configure(MapperFeature.USE_STD_BEAN_NAMING, true)
                .defaultUseWrapper(true)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        XmlConverter xmlConverter = new JacksonXmlConverter(xmlMapper);
        BuildSystemFileGenerator buildSystemFileGenerator = new MavenBuildSystemFileGenerator(xmlConverter);
        ProjectGenerator generator = new ProjectGeneratorImpl(buildSystemFileGenerator);
        FileTreeNode projectRootTree = generator.generate(project);

        try {
            projectRootTree.write();
        } catch (IOException e) {
            e.printStackTrace();
        }

        makeAndRun(project);
    }

    private static void makeAndRun(Project project) {
        CommandExecutor executor = new JavaRuntimeCommandExecutor();
        try {
            File projectRootFile = new File(project.getLocation(), project.getName());

            int resultValue = executor.execute("mvn clean package", projectRootFile);
            assert resultValue == 0;

            String jarFileName = project.getArtifactId() + "-" + project.getVersion() + ".jar";
            resultValue = executor.execute("java -jar " + jarFileName, new File(projectRootFile, "target"), new SimpleExecuteListener() {
                @Override
                public void out(String line) {
                    assert line.equals("hello world");
                }
            });
            assert resultValue == 0;

            resultValue = executor.execute("mvn clean", projectRootFile);
            assert resultValue == 0;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
