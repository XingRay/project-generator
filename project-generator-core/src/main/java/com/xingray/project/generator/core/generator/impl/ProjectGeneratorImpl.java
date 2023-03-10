package com.xingray.project.generator.core.generator.impl;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.xingray.project.generator.core.entity.FileTreeNode;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.generator.BuildSystemFileGenerator;
import com.xingray.project.generator.core.generator.ProjectGenerator;

import java.io.File;

public class ProjectGeneratorImpl implements ProjectGenerator {

    private final BuildSystemFileGenerator buildSystemFileGenerator;

    public ProjectGeneratorImpl(BuildSystemFileGenerator buildSystemFileGenerator) {
        this.buildSystemFileGenerator = buildSystemFileGenerator;
    }

    @Override
    public FileTreeNode generate(Project project) {
        FileTreeNode projectRootTree = FileTreeNode.createTree(new File(project.getLocation(), project.getName()));

        projectRootTree.addAndGetDescendantTree("src", "main")
                /**/.addAndGetChildTree("java").parent()
                /**/.addAndGetChildTree("resource");

        FileTreeNode javaRootNode = projectRootTree.descendant("src", "main", "java");

        String packageName = project.getGroupId() + "." + project.getArtifactId().replaceAll("-", ".");
        String mainClassName = "Main";
        project.setMainClass(packageName+"."+mainClassName);

        String[] packageNames = packageName.split("\\.");
        FileTreeNode packageRootTree = javaRootNode.addAndGetDescendantTree(packageNames);

        String content = generateCode(packageName, mainClassName);
        packageRootTree.addChildNode(mainClassName+".java", content);

        FileTreeNode buildSystemFile = buildSystemFileGenerator.generatorBuildSystemFile(project);
        projectRootTree.addChild(buildSystemFile);

        return projectRootTree;
    }

    private static String generateCode(String packageName, String className) {
        CompilationUnit cu = new CompilationUnit();

        // Add package declaration
        cu.setPackageDeclaration(packageName);


        ClassOrInterfaceDeclaration mainClass = cu.addClass(className).setPublic(true);
        MethodDeclaration mainMethod = mainClass.addMethod("main", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
        mainMethod.setType(void.class);
        mainMethod.addParameter(String[].class, "args");

        BlockStmt body = new BlockStmt();

        ExpressionStmt statement = new ExpressionStmt();
        MethodCallExpr methodCallExpression = new MethodCallExpr(new FieldAccessExpr(new NameExpr("System"), "out"), "println", NodeList.nodeList(new StringLiteralExpr("hello world")));
        statement.setExpression(methodCallExpression);
        body.addStatement(statement);
        mainMethod.setBody(body);

        return cu.toString();
    }
}
