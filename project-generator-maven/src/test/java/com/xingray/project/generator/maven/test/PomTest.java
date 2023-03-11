package com.xingray.project.generator.maven.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xingray.project.generator.maven.entity.ProjectObjectModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PomTest {
    @Test
    public void test() {
        XmlMapper xmlMapper = XmlMapper.builder()
                .configure(MapperFeature.USE_STD_BEAN_NAMING, true)
                .defaultUseWrapper(true)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();

        ProjectObjectModel projectObjectModel = null;
        try {
            projectObjectModel = xmlMapper.readValue(new File("src/test/resources/pom.xml"), ProjectObjectModel.class);
            xmlMapper.writeValue(new File("src/test/resources/pom2.xml"), projectObjectModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(projectObjectModel);
    }

}
