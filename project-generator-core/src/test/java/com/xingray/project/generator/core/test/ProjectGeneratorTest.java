package com.xingray.project.generator.core.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xingray.java.command.CommandExecutor;
import com.xingray.java.command.CommandResult;
import com.xingray.java.command.JavaRuntimeCommandExecutor;
import com.xingray.java.util.SystemUtil;
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
        project.setLocation("D:\\code\\workspace\\java");
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
            String cmd = SystemUtil.isRunOnWindows() ? "mvn.cmd clean package" : "mvn clean package";
            File projectRootFile = new File(project.getLocation(), project.getName());
            CommandResult result01 = executor.execute(cmd, projectRootFile);
            System.out.println(result01);
            String jarFileName = project.getArtifactId() + "-" + project.getVersion() + ".jar";
            CommandResult result02 = executor.execute("java -jar " + jarFileName, new File(projectRootFile, "target"));
            System.out.println(result02);
            assert result02.getResultStringList().get(0).equals("hello world");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
