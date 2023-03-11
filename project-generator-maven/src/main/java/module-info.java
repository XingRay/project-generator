module com.xingray.project.generator.maven {
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.annotation;

    exports com.xingray.project.generator.maven.entity;
    opens com.xingray.project.generator.maven.entity;
}