package sqlServer;

import common.Database;

public class SqlServerBasic implements Database {
    private Long serialVersionUID = 1l;

    @Override
    public String queryAllTable() {
        return null;
    }

    public String getTableCreateSql(String tableName) {
        return "sqlServer";
    }
}
