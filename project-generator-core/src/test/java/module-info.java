module com.xingray.java.project.generator.test {
    requires org.junit.jupiter;
    requires com.xingray.project.generator.core;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires com.github.javaparser.core;
    requires com.github.javaparser.symbolsolver.core;

    exports com.xingray.project.generator.core.test;
}