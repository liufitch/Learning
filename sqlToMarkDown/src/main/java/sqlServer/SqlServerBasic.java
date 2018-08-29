package sqlServer;

import common.Database;
import entity.TableEntity;

import java.util.List;

public class SqlServerBasic implements Database {
    private Long serialVersionUID = 1l;

    //参考： https://blog.csdn.net/mengmakies/article/details/51527337
    @Override
    public String queryAllTable() {
        return "select name from YDK..SysObjects where XType=’U’ order by name";
    }

    public String getTableCreateSql(String tableName) {
        return "select * from " + tableName;
    }

    @Override
    public String descTable(String tableName) {
        return null;
    }

    @Override
    public List<TableEntity> buildTable(List<String> list) {

        return null;
    }

    @Override
    public List<String> getFieldStatement(String sqlStatement) {
        return null;
    }
}
