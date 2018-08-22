package Context;

import Exceptions.FileRelativeException;
import common.File.FileInfo;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
            String createStatement = null;
            while (resultSet.next()){
                tableNameList.add(resultSet.getString(1));
            }
            resultSet.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tableNameList;

    }

}
