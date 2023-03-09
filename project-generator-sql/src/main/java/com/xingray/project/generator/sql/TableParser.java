package com.xingray.project.generator.sql;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;

public interface TableParser {

    Integer getVarcharColumnLength(FieldDeclaration fieldDeclaration);

    boolean isMapToTable(ClassOrInterfaceDeclaration classOrInterfaceDeclaration);

    String getTableName(ClassOrInterfaceDeclaration classOrInterfaceDeclaration);

    boolean isNullable(FieldDeclaration fieldDeclaration);

    String getDefaultValue(FieldDeclaration fieldDeclaration);
}
