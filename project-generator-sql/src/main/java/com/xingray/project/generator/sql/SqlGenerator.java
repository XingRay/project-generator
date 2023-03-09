package com.xingray.project.generator.sql;

import com.xingray.project.generator.sql.entity.Column;
import com.xingray.project.generator.sql.entity.ColumnType;
import com.xingray.project.generator.sql.entity.Table;

public class SqlGenerator {

    /**
     * SET NAMES utf8mb4;
     * SET FOREIGN_KEY_CHECKS = 0;
     * <p>
     * -- ----------------------------
     * -- Table structure for stock_quote
     * -- ----------------------------
     * DROP TABLE IF EXISTS `stock_quote`;
     * CREATE TABLE `stock_quote`  (
     * `id` bigint NOT NULL AUTO_INCREMENT,
     * `symbol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
     * `open` double NOT NULL,
     * `close` double NOT NULL,
     * `high` double NOT NULL,
     * `low` double NOT NULL,
     * `volume` bigint NULL DEFAULT NULL,
     * `amount` double NULL DEFAULT NULL,
     * `timestamp` bigint NOT NULL,
     * PRIMARY KEY (`id`) USING BTREE,
     * INDEX `stock_quote_index_symbol_timestamp`(`symbol` ASC, `timestamp` DESC) USING BTREE
     * ) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
     * <p>
     * SET FOREIGN_KEY_CHECKS = 1;
     *
     * @param table
     * @return
     */
    public String generateCreateTableSql(Table table) {
//        String sql =
//                "CREATE TABLE `stock_quote`  (\n" +
//
//                "  `id` bigint NOT NULL AUTO_INCREMENT,\n" +
//                "  `symbol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,\n" +
//                "  `open` double NOT NULL,\n" +
//                "  `close` double NOT NULL,\n" +
//                "  `high` double NOT NULL,\n" +
//                "  `low` double NOT NULL,\n" +
//                "  `volume` bigint NULL DEFAULT NULL,\n" +
//                "  `amount` double NULL DEFAULT NULL,\n" +
//                "  `timestamp` bigint NOT NULL,\n" +
//                "  PRIMARY KEY (`id`) USING BTREE,\n" +
//                "  INDEX `stock_quote_index_symbol_timestamp`(`symbol` ASC, `timestamp` DESC) USING BTREE\n" +
//                ") ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;\n" +
//                "\n" +
//                "SET FOREIGN_KEY_CHECKS = 1;";
        StringBuilder builder = new StringBuilder();
        builder.append("SET NAMES utf8mb4;\n");
        builder.append("SET FOREIGN_KEY_CHECKS = 0;\n");
        builder.append("DROP TABLE IF EXISTS `").append(table.getName()).append("`;\n");
        builder.append("CREATE TABLE `").append(table.getName()).append("`  (\n");
        for (Column column : table.getColumnList()) {
            builder.append("  `").append(column.getName()).append("` ");

            builder.append(column.getColumnType().getName());
            if (column.getColumnType() == ColumnType.VARCHAR) {
                builder.append("(").append(column.getLength()).append(")");
                builder.append(" CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci");
            }

            builder.append(" ");

            if (column.isNullable()) {
                builder.append("NULL");
                String defaultValue = column.getDefaultValue();
                if (defaultValue != null) {
                    builder.append(" DEFAULT ").append(defaultValue);
                }
            } else {
                builder.append("NOT NULL");
            }
            if (column.isAutoIncrement()) {
                builder.append(" AUTO_INCREMENT");
            }
            builder.append(",\n");
        }
        builder.append("  PRIMARY KEY (`id`) USING BTREE\n");
        builder.append(") ENGINE = ").append(table.getEngine());
        builder.append(" CHARACTER SET = ").append(table.getCharacterSet());
        builder.append(" COLLATE = ").append(table.getCollate());
        builder.append(" ROW_FORMAT = ").append(table.getRowFormat());
        builder.append(";\n");
        builder.append("\n").append("SET FOREIGN_KEY_CHECKS = 1;");

        return builder.toString();
    }
}
