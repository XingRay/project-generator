package com.xingray.project.generator.core.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.xingray.project.generator.core.entity.BuildSystem;
import com.xingray.project.generator.core.entity.Language;
import com.xingray.project.generator.core.entity.Project;
import com.xingray.project.generator.core.maven.entity.ProjectObjectModel;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class MavenProjectGeneratorTest {
    @Test
    public void test01() {
        Project project = new Project();
        project.setName("generator-test01");
        project.setLocation("D:\\code\\workspace\\java");
        project.setLanguage(Language.JAVA);
        project.setBuildSystem(BuildSystem.MAVEN);
        project.setGroupId("com.xingray");
        project.setArtifactId("generator-test01");
        project.setVersion("1.0.0");

        XmlMapper xmlMapper = XmlMapper.builder()
                .configure(MapperFeature.USE_STD_BEAN_NAMING, true)
                .defaultUseWrapper(true)
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build();


        File projectRootFile = new File(new File(project.getLocation()), project.getName());
        if (projectRootFile.exists()) {
            projectRootFile.delete();
        }
        projectRootFile.mkdir();

        if (project.getBuildSystem() == BuildSystem.MAVEN) {
            File pomFile = new File(projectRootFile, "pom.xml");
            ProjectObjectModel pom = new ProjectObjectModel();
            pom.setGroupId(project.getGroupId());
            pom.setArtifactId(project.getArtifactId());
            pom.setVersion(project.getVersion());

            HashMap<String, String> properties = new HashMap<>();
            properties.put("maven.compiler.source", "19");
            properties.put("maven.compiler.target", "19");
            properties.put("project.build.sourceEncoding", "UTF-8");
            pom.setProperties(properties);

            try {
                xmlMapper.writeValue(new FileWriter(pomFile, StandardCharsets.UTF_8), pom);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        File srcDir = new File(projectRootFile, "src");
        srcDir.mkdir();

        File mainDir = new File(srcDir, "main");
        mainDir.mkdir();

        File javaDir = new File(mainDir, "java");
        javaDir.mkdir();

        File resourceDir = new File(mainDir, "resource");
        resourceDir.mkdir();


        CompilationUnit cu = new CompilationUnit();

        // Add package declaration
        String packageName = project.getGroupId() + "." + project.getArtifactId().replaceAll("-", ".");
        cu.setPackageDeclaration(packageName);

        String className = "Main";
        ClassOrInterfaceDeclaration mainClass = cu.addClass(className).setPublic(true);
        MethodDeclaration mainMethod = mainClass.addMethod("main", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
        mainMethod.setType(Void.class);
        mainMethod.addParameter(String[].class, "args");

        BlockStmt body = new BlockStmt();
        StringLiteralExpr helloWorld = new StringLiteralExpr("hello world");

        ExpressionStmt statement = new ExpressionStmt();
        MethodCallExpr methodCallExpression = new MethodCallExpr();
        FieldAccessExpr scope = new FieldAccessExpr();

        scope.setScope(new NameExpr("System"));

        scope.setName("out");
        methodCallExpression.setScope(scope);
        methodCallExpression.setName("println");
        methodCallExpression.addArgument(new StringLiteralExpr("hello world"));
        statement.setExpression(methodCallExpression);
        body.addStatement(statement);
        mainMethod.setBody(body);

        String path = CodeGenerationUtils.packageToPath(packageName);
        File packageFile = new File(javaDir, path);
        packageFile.mkdirs();

        File file = new File(packageFile, className+".java");
        try {
            Files.writeString(file.toPath(), cu.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String testClassPath = "D:\\code\\git\\github\\project-generator\\project-generator-core\\src\\test\\java";
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(MavenProjectGeneratorTest.class).resolve(testClassPath));
        System.out.println(sourceRoot);


        try {
            List<ParseResult<CompilationUnit>> parseResults = sourceRoot.tryToParse();
            System.out.println(parseResults);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Add variable declaration
//                            VariableDeclarator message = new VariableDeclarator(new ClassOrInterfaceType(String.class.getName()), "message", new StringLiteralExpr("Hello, world!"));
//
//                            // Add statement expression
//                            ExpressionStmt println = new ExpressionStmt(new StringLiteralExpr("System.out.println(message);"));
//
//                            // Add statement block
//                            BlockStmt block = new BlockStmt();
//                            block.addStatement(new VariableDeclarator(PrimitiveType.intType(), "x", "5"));
//                            block.addStatement(println);
//                            block.addStatement(new ReturnStmt());
//
//                            // Add method declaration
//                            body.addStatement(message);
//                            new MethodDeclaration(
//                                    EnumSet.of(Modifier.PUBLIC, Modifier.STATIC),
//                                    new VoidType(),
//                                    "main")
//                                    .addParameter(
//                                            new Parameter(PrimitiveType.intType().createArrayType(), "args"))
//                                    .setBody(block)
//                                    .addAndGetParameter(message));
    }
}
