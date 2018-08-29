package Context;

import Exceptions.FileRelativeException;
import common.File.FileInfo;
import entity.TableEntity;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class SQLExecute {
    //声明Connection对象
    private  Connection con;
    //驱动程序名
    private   String driver = "";
    //URL指向要访问的数据库名mydata
    private  String url = "";
    //MySQL配置时的用户名
    private  String user = "";
    //MySQL配置时的密码
    private   String password = "";

    private  Statement statement =null;

    public void initDataBase(){
        FileInfo fileInfo = FileInfo.getInstance();
        //its that pass the value , so nothing will change.
        // reference: https://www.cnblogs.com/binyue/p/3862276.html
        Map map = fileInfo.readPropertiesFile();
        this.driver = map.get("driver")==null ? this.driver:map.get("driver").toString();
        this.url = map.get("url")==null ? this.url:map.get("url").toString();
        this.user = map.get("user")==null ? this.user:map.get("user").toString();
        this.password = map.get("password")==null ? this.password: map.get("password").toString();

    }

    public void connectDatabase(){
        try{
            Class.forName(driver);
            //1. connect mysql database
             con = DriverManager.getConnection(url, user, password);
            if(con.isClosed())
                System.out.println("fail to connect to database");

            // create statement object  to execute sql command.
             statement = con.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void shutDown(){
        try{
            statement.close();
            con.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> getTableNameList(String sql){

        List<String> tableNameList = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            //Field Type Null Key Default  Extra
            String tableName = null;
            while (resultSet.next()){
                tableNameList.add(resultSet.getString(1));
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tableNameList;

    }

    public Map<String, String> getTableCreateStatement(String sql){
        Map<String, String> map = new HashMap<>();
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                map.put(resultSet.getString(1), resultSet.getString(2));
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;

    }

    public List<TableEntity> getResultSetBySql(String sql) {
        List<TableEntity> list = new ArrayList<>();
        try {
            //https://docs.microsoft.com/en-us/sql/connect/jdbc/reference/getprimarykeys-method-sqlserverdatabasemetadata?view=sql-server-2017
            //主键
//            String primaryKey = con.getMetaData().getPrimaryKeys(conn.getCatalog(),null, tablename)
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd  = rs.getMetaData();
            int count = rsmd.getColumnCount();



            for(int i=1; i<=count; i++){
                TableEntity tableEntity = new TableEntity();
                tableEntity.setField(rsmd.getCatalogName(i));
                tableEntity.setType(String.valueOf(rsmd.getColumnType(i)));
//                tableEntity.setSnull(rsmd.));
            }
            rs.close();

        } catch (Exception se) {
            se.printStackTrace();
        }
        return null;
    }



    public void descTableExec(String sql){
        try {
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                System.out.println(resultSet.getString("1"));
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
