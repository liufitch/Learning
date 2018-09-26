import Context.DataBaseContext;
import Context.SQLExecute;
import common.Database;
import common.File.FileInfo;
import entity.TableEntity;
import mysql.MysqlBasic;
import java.util.*;

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
                List<String> list = context.getFieldStatement(map.get(m));

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


            System.out.println("end");

//            fileInfo.writeFileAppend(list, pathName);

//            System.out.println(new StringBuilder().append("开始插入表").append(name ).append("字段").append(field).toString());

        }catch (Exception  e){
            e.printStackTrace();
        }

    }


}
