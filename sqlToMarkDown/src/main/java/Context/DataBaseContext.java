package Context;

import common.Database;

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
}
