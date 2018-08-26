package sqlServer;

import common.Database;
import entity.TableEntity;

import java.util.List;

public class SqlServerBasic implements Database {
    private Long serialVersionUID = 1l;

    @Override
    public String queryAllTable() {
        return null;
    }

    public String getTableCreateSql(String tableName) {
        return "sqlServer";
    }

    @Override
    public String descTable(String tableName) {
        return null;
    }

    @Override
    public List<TableEntity> buildTable(List<String> list) {
        return null;
    }
}
