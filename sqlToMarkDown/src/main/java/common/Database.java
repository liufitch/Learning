package common;

import entity.TableEntity;

import java.util.List;

public interface Database {
    String getTableCreateSql(String tableName);
    String queryAllTable();
    String descTable(String tableName);
    List<TableEntity> buildTable(List<String> list);
}
