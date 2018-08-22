import Context.DataBaseContext;
import Context.SQLExecute;
import Exceptions.FileRelativeException;
import common.Database;
import common.File.FileInfo;
import mysql.MysqlBasic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class Main {


    public static void main(String[] args){
        System.out.println("start");
        try{
            Database mysqlBasic = new MysqlBasic();

            DataBaseContext context = new DataBaseContext(mysqlBasic);


//            String sql = context.getTableCreateSql("home_page_banner");
            String sql = context.queryAllTable();

            SQLExecute sqlExecute = new SQLExecute();
            sqlExecute.initDataBase();
            sqlExecute.connectDatabase();

            List<String> tableNameList = sqlExecute.getTableNameList(sql);



            //create file
            FileInfo fileInfo = FileInfo.getInstance();
            String pathName = fileInfo.readPropertiesFilePathName();
            fileInfo.createFile(pathName);


        }catch (FileRelativeException e){
            e.printStackTrace();
        }

    }


}
