package com.xingray.project.generator.sql;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.utils.SourceRoot;
import com.xingray.project.generator.sql.entity.Column;
import com.xingray.project.generator.sql.entity.ColumnType;
import com.xingray.project.generator.sql.entity.Table;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectParser {

    public List<Table> parse(SourceRoot sourceRoot) {

        List<Table> tables = new ArrayList<>();

        try {
            List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
            for (ParseResult<CompilationUnit> parseResult : parseResults) {
                Optional<CompilationUnit> optional = parseResult.getResult();
                if (optional.isEmpty()) {
                    continue;
                }
                CompilationUnit compilationUnit = optional.get();
                NodeList<TypeDeclaration<?>> types = compilationUnit.getTypes();
                for (TypeDeclaration<?> type : types) {
                    if (type instanceof ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
                        if (classOrInterfaceDeclaration.isInterface()) {
                            continue;
                        }

                        String name = classOrInterfaceDeclaration.getName().toString();
                        if (name.endsWith("Model")) {
                            Table table = new Table();
                            NodeList<AnnotationExpr> annotations = classOrInterfaceDeclaration.getAnnotations();
                            for (AnnotationExpr annotation : annotations) {
                                if (annotation instanceof SingleMemberAnnotationExpr singleMemberAnnotationExpr) {
                                    if (singleMemberAnnotationExpr.getName().getIdentifier().equals("TableName")) {
                                        String tableName = singleMemberAnnotationExpr.getMemberValue().asStringLiteralExpr().getValue();
                                        table.setName(tableName);
                                    }
                                }
                            }
                            if (table.getName() == null) {
                                table.setName(name);
                            }

                            NodeList<BodyDeclaration<?>> members = classOrInterfaceDeclaration.getMembers();
                            for (BodyDeclaration<?> bodyDeclaration : members) {
                                if (bodyDeclaration instanceof FieldDeclaration fieldDeclaration) {
                                    NodeList<VariableDeclarator> variables = fieldDeclaration.getVariables();
                                    for (VariableDeclarator variable : variables) {
                                        String variableName = variable.getName().getIdentifier();
                                        Type variableType = variable.getType();
                                        Column column = new Column();
                                        column.setName(variableName);

                                        if (variableType instanceof ClassOrInterfaceType classOrInterfaceType) {
                                            String typeIdentifier = classOrInterfaceType.getName().getIdentifier();
                                            System.out.println(variableName + " " + typeIdentifier);
                                            switch (typeIdentifier) {
                                                case "Integer" -> {
                                                    column.setColumnType(ColumnType.INT);
                                                    column.setNullable(true);
                                                    column.setDefaultValue("NULL");
                                                }
                                                case "Long" -> {
                                                    column.setColumnType(ColumnType.BIG_INT);
                                                    column.setNullable(true);
                                                    column.setDefaultValue("NULL");
                                                }
                                                case "Double" -> {
                                                    column.setColumnType(ColumnType.DOUBLE);
                                                    column.setNullable(true);
                                                    column.setDefaultValue("NULL");
                                                }
                                                case "String" -> {
                                                    column.setColumnType(ColumnType.VARCHAR);
                                                    column.setLength(100);
                                                }
                                            }
                                        } else if (variableType instanceof PrimitiveType primitiveType) {
                                            String primitiveTypeName = primitiveType.getType().asString();
                                            System.out.println(variableName + " " + primitiveTypeName);

                                            switch (primitiveTypeName) {
                                                case "int" -> {
                                                    column.setColumnType(ColumnType.INT);
                                                    column.setNullable(false);
                                                }
                                                case "long" -> {
                                                    column.setColumnType(ColumnType.BIG_INT);
                                                    column.setNullable(false);
                                                }
                                                case "double" -> {
                                                    column.setColumnType(ColumnType.DOUBLE);
                                                    column.setNullable(false);
                                                }
                                            }
                                        }

                                        table.addColumn(column);
                                    }
                                }
                            }

                            tables.add(table);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return tables;
    }
}
