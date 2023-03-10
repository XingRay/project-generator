module com.xingray.java.project.generator.test {
    requires org.junit.jupiter;
    requires com.xingray.project.generator.core;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires com.github.javaparser.core;
    requires com.github.javaparser.symbolsolver.core;

    requires com.xingray.java.command;
    requires com.xingray.java.util;

    requires org.slf4j;

    exports com.xingray.project.generator.core.test;
}