package com.xingray.project.generator.sql.entity;

public class Column {
    private String name;
    private ColumnType columnType;

    private Integer length;

    private Integer decimalLength;

    private boolean isNullable;

    private String defaultValue;

    private boolean isVirtual;

    private boolean isAutoIncrement;

    private Integer key;

    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnType getColumnType() {
        return columnType;
    }

    public void setColumnType(ColumnType columnType) {
        this.columnType = columnType;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getDecimalLength() {
        return decimalLength;
    }

    public void setDecimalLength(Integer decimalLength) {
        this.decimalLength = decimalLength;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public boolean isVirtual() {
        return isVirtual;
    }

    public void setVirtual(boolean virtual) {
        isVirtual = virtual;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", columnType=" + columnType +
                ", length=" + length +
                ", decimalLength=" + decimalLength +
                ", isNullable=" + isNullable +
                ", isVirtual=" + isVirtual +
                ", isAutoIncrement=" + isAutoIncrement +
                ", key=" + key +
                ", comment='" + comment + '\'' +
                '}';
    }
}
