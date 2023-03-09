package com.xingray.project.generator.sql.mybatisplus;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.xingray.project.generator.sql.TableParser;

public class MybatisPlusTableParser implements TableParser {
    public Integer getVarcharColumnLength(FieldDeclaration fieldDeclaration) {
        return 100;
    }

    public String getDefaultValue(FieldDeclaration fieldDeclaration) {
        if (isNullable(fieldDeclaration)) {
            return "NULL";
        } else {
            return null;
        }
    }

    public String getTableName(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        String tableName = null;
        NodeList<AnnotationExpr> annotations = classOrInterfaceDeclaration.getAnnotations();
        for (AnnotationExpr annotation : annotations) {
            if (annotation instanceof SingleMemberAnnotationExpr singleMemberAnnotationExpr) {
                if (singleMemberAnnotationExpr.getName().getIdentifier().equals("TableName")) {
                    tableName = singleMemberAnnotationExpr.getMemberValue().asStringLiteralExpr().getValue();
                }
            }
        }
        if (tableName == null) {
            tableName = classOrInterfaceDeclaration.getName().toString();
        }
        return tableName;
    }

    public boolean isMapToTable(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
        return classOrInterfaceDeclaration.getName().toString().endsWith("Model");
    }

    public boolean isNullable(FieldDeclaration fieldDeclaration) {
        boolean isNullable = false;
        NodeList<AnnotationExpr> fieldAnnotations = fieldDeclaration.getAnnotations();
        if (fieldAnnotations != null && !fieldAnnotations.isEmpty()) {
            for (AnnotationExpr annotationExpr : fieldAnnotations) {
                if (annotationExpr.getName().getIdentifier().equals("TableId")) {
                    isNullable = true;
                    break;
                }
            }
        }
        return isNullable;
    }
}
