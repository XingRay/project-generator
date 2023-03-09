package com.xingray.project.generator.sql.entity;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private String name;
    private final List<Column> columnList;

    private String engine = "InnoDB";

    private String characterSet = "utf8mb4";

    private String collate = "utf8mb4_0900_ai_ci";

    private String rowFormat = "Dynamic";

    public Table() {
        columnList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addColumn(Column column) {
        columnList.add(column);
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getCharacterSet() {
        return characterSet;
    }

    public void setCharacterSet(String characterSet) {
        this.characterSet = characterSet;
    }

    public String getCollate() {
        return collate;
    }

    public void setCollate(String collate) {
        this.collate = collate;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat;
    }

    @Override
    public String toString() {
        return "Table{" +
                "name='" + name + '\'' +
                ", columnList=" + columnList +
                ", engine='" + engine + '\'' +
                ", characterSet='" + characterSet + '\'' +
                ", collate='" + collate + '\'' +
                ", rowFormat='" + rowFormat + '\'' +
                '}';
    }
}
