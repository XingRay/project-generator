module com.xingray.project.generator.core {
    exports com.xingray.project.generator.core.entity;
    exports com.xingray.project.generator.core.maven;
    exports com.xingray.project.generator.core.maven.entity;

    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.annotation;

    requires com.github.javaparser.core;
    requires com.github.javaparser.symbolsolver.core;
}