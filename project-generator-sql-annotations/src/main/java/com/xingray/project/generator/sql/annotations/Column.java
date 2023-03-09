package com.xingray.project.generator.sql.annotations;

public @interface Column {
    String value();

    String name();

    boolean isNullable();

    String defaultValue();

    int charLength();
}
