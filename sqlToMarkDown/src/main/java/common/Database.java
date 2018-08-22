package common;

public interface Database {
    String getTableCreateSql(String tableName);
    String queryAllTable();
}
