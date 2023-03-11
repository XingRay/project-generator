package com.xingray.project.generator.maven.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "scope")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Scope {
    TEST("test"),
    COMPILE("compile"),
    RUNTIME("runtime"),
    SYSTEM("system"),
    PROVIDED("provided"),
    IMPORT("import");

    private final String value;

    Scope(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static Scope fromValue(String value) {
        for (Scope scope : Scope.values()) {
            if (scope.value.equals(value)) {
                return scope;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
