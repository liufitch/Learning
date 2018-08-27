import Context.DataBaseContext;
import Context.SQLExecute;
import Exceptions.FileRelativeException;
import common.Database;
import common.File.FileInfo;
import common.Util;
import constants.Constants;
import entity.TableEntity;
import mysql.MysqlBasic;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import static common.Util.nullToEmpty;


public class Main {


    public static void main(String[] args){
        System.out.println("start");
        try{
            Database mysqlBasic = new MysqlBasic();

            DataBaseContext context = new DataBaseContext(mysqlBasic);


            String sql = context.queryAllTable();

            SQLExecute sqlExecute = new SQLExecute();
            sqlExecute.initDataBase();
            sqlExecute.connectDatabase();

            List<String> tableNameList = sqlExecute.getTableNameList(sql);
            tableNameList.forEach(m->{

                String createTableSql = context.getTableCreateSql(m);


                Map<String, String> map = sqlExecute.getTableCreateStatement(createTableSql);
                String createStatement = map.get(m);
                int end = createStatement.lastIndexOf(")");
                int start = createStatement.indexOf("(");
                String middleStatement = createStatement.substring(start+1, end);
                System.out.println(middleStatement);
                String[] fieldStatement = middleStatement.replace("`","").replace("\n","").split(",");
                List<String> list = Arrays.asList(fieldStatement).stream().map(p ->p.trim()).collect(Collectors.toList());

                List<TableEntity> buildTableList = context.buildTable(list);

                for(int i=0; i< buildTableList.size(); i++){
                    nullToEmpty(buildTableList.get(i));
                }
                //create file
                FileInfo fileInfo = FileInfo.getInstance();
                String pathName = fileInfo.readPropertiesFilePathName();
                //write class to file
                fileInfo.serializeTableEntity(buildTableList, pathName, m);
            });



//            fileInfo.writeFileAppend(list, pathName);



        }catch (Exception  e){
            e.printStackTrace();
        }

    }


}
