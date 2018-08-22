package mysql;

import common.Database;

public class MysqlBasic implements Database {
    private Long serialVersionUID = 1L;

    public String getTableCreateSql(String tableName){
        return "show create table " + tableName;
    }

    @Override
    public String queryAllTable() {
        return "show tables";
    }


}
