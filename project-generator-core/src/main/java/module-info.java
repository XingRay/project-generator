module com.xingray.project.generator.core {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires com.github.javaparser.core;
    requires com.github.javaparser.symbolsolver.core;

    requires com.xingray.java.maven.core;
    requires com.xingray.java.util;

    exports com.xingray.project.generator.core.entity;
    exports com.xingray.project.generator.core.generator;
    exports com.xingray.project.generator.core.generator.impl;
    exports com.xingray.project.generator.core.maven;
    exports com.xingray.project.generator.core.gradle;
}