package com.xingray.project.generator.sql.test;

import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.xingray.project.generator.sql.ProjectParser;
import com.xingray.project.generator.sql.SqlGenerator;
import com.xingray.project.generator.sql.entity.Table;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SqlGeneratorTest {
    @Test
    public void test() {
        ProjectParser projectParser = new ProjectParser();
        String path = "D:\\code\\workspace\\trade\\QuantPlatform\\quant-platform\\quant-platform-stock\\src\\main\\java";
        SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(ProjectParser.class).resolve(path));

        List<Table> tables = projectParser.parse(sourceRoot);
        SqlGenerator sqlGenerator = new SqlGenerator();

        for (Table table : tables) {
            String sql = sqlGenerator.generateCreateTableSql(table);
            System.out.println(sql);
        }
    }

}
