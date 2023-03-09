package com.xingray.project.generator.sql.entity;

public enum ColumnType {
    BIG_INT("bigint"),
    INT("int"),
    VARCHAR("varchar"),

    DOUBLE("double");

    private final String name;

    ColumnType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
