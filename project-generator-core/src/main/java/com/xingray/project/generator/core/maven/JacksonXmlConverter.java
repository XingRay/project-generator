package com.xingray.project.generator.core.maven;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class JacksonXmlConverter implements XmlConverter {

    private final XmlMapper xmlMapper;

    public JacksonXmlConverter(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public String objectToXml(Object o) {
        try {
            return xmlMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T xmlToIbject(String s, Class<T> cls) {
        try {
            return xmlMapper.readValue(s, cls);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
