package com.xingray.project.generator.sql;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.utils.SourceRoot;
import com.xingray.project.generator.sql.entity.Column;
import com.xingray.project.generator.sql.entity.ColumnType;
import com.xingray.project.generator.sql.entity.Table;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectParser {

    public List<Table> parse(String path, TableParser tableParser) {
        return parse(Path.of(path), tableParser);
    }

    public List<Table> parse(Path path, TableParser tableParser) {
        return parse(new SourceRoot(path), tableParser);
    }

    private List<Table> parse(SourceRoot sourceRoot, TableParser tableParser) {

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

                        if (tableParser.isMapToTable(classOrInterfaceDeclaration)) {
                            Table table = new Table();
                            table.setName(tableParser.getTableName(classOrInterfaceDeclaration));

                            NodeList<BodyDeclaration<?>> members = classOrInterfaceDeclaration.getMembers();
                            for (BodyDeclaration<?> bodyDeclaration : members) {
                                if (bodyDeclaration instanceof FieldDeclaration fieldDeclaration) {
                                    boolean isNullable = tableParser.isNullable(fieldDeclaration);
                                    String defaultValue = tableParser.getDefaultValue(fieldDeclaration);
                                    NodeList<VariableDeclarator> variables = fieldDeclaration.getVariables();

                                    for (VariableDeclarator variable : variables) {
                                        String variableName = variable.getName().getIdentifier();
                                        Type variableType = variable.getType();
                                        Column column = new Column();
                                        column.setName(variableName);
                                        column.setNullable(isNullable);
                                        column.setDefaultValue(defaultValue);


                                        if (variableType instanceof ClassOrInterfaceType classOrInterfaceType) {
                                            String typeIdentifier = classOrInterfaceType.getName().getIdentifier();
                                            switch (typeIdentifier) {
                                                case "Integer" -> {
                                                    column.setColumnType(ColumnType.INT);
                                                }
                                                case "Long" -> {
                                                    column.setColumnType(ColumnType.BIG_INT);
                                                }
                                                case "Double" -> {
                                                    column.setColumnType(ColumnType.DOUBLE);
                                                }
                                                case "String" -> {
                                                    column.setColumnType(ColumnType.VARCHAR);
                                                    column.setLength(tableParser.getVarcharColumnLength(fieldDeclaration));
                                                }
                                            }
                                        } else if (variableType instanceof PrimitiveType primitiveType) {
                                            String primitiveTypeName = primitiveType.getType().asString();

                                            switch (primitiveTypeName) {
                                                case "int" -> {
                                                    column.setColumnType(ColumnType.INT);
                                                }
                                                case "long" -> {
                                                    column.setColumnType(ColumnType.BIG_INT);
                                                }
                                                case "double" -> {
                                                    column.setColumnType(ColumnType.DOUBLE);
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
