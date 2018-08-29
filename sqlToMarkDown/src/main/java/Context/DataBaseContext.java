package Context;

import common.Database;
import entity.TableEntity;

import java.util.List;

public class DataBaseContext {
    private Database database;

    public DataBaseContext(Database database){
        this.database = database;
    }

    public  String getTableCreateSql(String tableName){
        return database.getTableCreateSql(tableName);
    }

    public String queryAllTable(){
        return database.queryAllTable();
    }

    public String descTable(String tableName){
        return database.descTable(tableName);
    }
    public List<TableEntity> buildTable(List<String> list){
        return database.buildTable(list);
    }
    public List<String> getFieldStatement(String sqlStatement){
        return database.getFieldStatement(sqlStatement);
    }
}
