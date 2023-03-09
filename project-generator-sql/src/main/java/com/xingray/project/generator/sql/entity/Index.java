package com.xingray.project.generator.sql.entity;

import java.util.List;

public class Index {
    private String name;

    private List<?> list;

    public static class IndexColumn{
        private String name;
        private String order;
    }
}
