package com.xingray.project.generator.sql;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

// TODO: 2023/3/9
public class DefaultAnnotationTableParser implements TableParser{
    @Override
    public Integer getVarcharColumnLength(FieldDeclaration fieldDeclaration) {
        return null;
    }

    @Override
    public boolean isMapToTable(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        return false;
    }

    @Override
    public String getTableName(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        return null;
    }

    @Override
    public boolean isNullable(FieldDeclaration fieldDeclaration) {
        return false;
    }

    @Override
    public String getDefaultValue(FieldDeclaration fieldDeclaration) {
        return null;
    }
}
