package mysql;

import common.Database;
import common.Util;
import constants.Constants;
import entity.TableEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static common.Util.getMethodInvoke;

public class MysqlBasic implements Database {

    private Long serialVersionUID = 1L;

    public String getTableCreateSql(String tableName){
        return "show create table " + tableName;
    }

    @Override
    public String queryAllTable() {
        return "show tables";
    }

    @Override
    public String descTable(String tableName) {
        return "desc "+ tableName;
    }
    @Override
    public List<TableEntity> buildTable(List<String> list) {
        List<TableEntity> entityList = new ArrayList<>();
        createTitle(entityList);
        Map<String, String> map = new HashMap<>();
        list.forEach(p ->{
            createField(p, entityList,map);
        });
        return entityList;
    }

    public void createTitle(List<TableEntity> entityList){
        TableEntity title = new TableEntity();
        title.setPrimaryKey("主键");
        title.setField("字段");
        title.setType("类型");
        title.setSnull("是否允许为null");
        title.setDefaultValue("默认值");
        title.setComment("备注");
        title.setExtra("其他");
        entityList.add(title);
        TableEntity titleLine = new TableEntity();
        titleLine.setPrimaryKey(Constants.Dashes);
        titleLine.setField(Constants.Dashes);
        titleLine.setType(Constants.Dashes);
        titleLine.setSnull(Constants.Dashes);
        titleLine.setDefaultValue(Constants.Dashes);
        titleLine.setComment(Constants.Dashes);
        titleLine.setExtra(Constants.Dashes);

        entityList.add(titleLine);
    }

    public void createField(String p,List<TableEntity> entityList, Map<String, String> map){
        TableEntity tableEntity = new TableEntity();
        int index =0;
        if(p.indexOf("PRIMARY KEY")>-1){
            String keyField = p.substring(p.indexOf("(")+1,p.lastIndexOf(")"));
            if(map.get(keyField) !=null && map.get(keyField).trim() !=""){
                tableEntity = entityList.stream().filter(m->m.getField().equals(keyField)).findFirst().get();
                tableEntity.setPrimaryKey(Constants.PrimaryKey);
            }
            return;// just like 'continue'  forEach  have nothing like 'break'
        }
        String field = p.substring(0, index= p.indexOf(" ")).trim().replaceAll("`","");

        map.put(field,field);
        String type = p.substring(++index, index=p.indexOf(" ",index) ).trim();
        tableEntity.setField(field);
        tableEntity.setType(type);

        if((index =p.indexOf("COMMENT"))>-1){
            int endIndex =p.substring(index+9).indexOf("'");
            tableEntity.setComment(p.substring(index+9, index+9+endIndex));
        }

        if( p.indexOf("DEFAULT NULL")>-1 || p.indexOf("DEFAULT") ==-1){
            tableEntity.setDefaultValue(Constants.DefaultValue);
        }else{
            index =p.indexOf("DEFAULT");
            int endIndex =p.substring(index+9).indexOf("'");
                 tableEntity.setDefaultValue(p.substring(index+9, index+9+endIndex));
        }

        if(p.indexOf("NOT NULL")>-1){
            tableEntity.setSnull(Constants.Y);
        }else{
            tableEntity.setSnull(Constants.N);
        }

        if(p.indexOf("AUTO_INCREMENT") >-1){
            tableEntity.setExtra(tableEntity.getExtra()==null ? Constants.AutoIn +" ":tableEntity.getExtra() + Constants.AutoIn +" ");
        }
        entityList.add(tableEntity);
    }

    public Map<String, String> enSurePrimaryKey(TableEntity entity){

        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(int i=0; i<fields.length; i++){
            if(fields[i].getName().equals("field")){
                Map<String, String> map = new HashMap<String, String>();
                map.put("field",getMethodInvoke(clazz).toString());
                return map;
            }
        }
        return  null;
    }

    // 字段 类型 是否允许为null  默认值 备注  自增 主键
//            `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'aaaa',
//                    `banner_image` varchar(256) DEFAULT NULL,
//            `banner_icon` varchar(256) DEFAULT NULL,
//            `banner_desc` varchar(1000) DEFAULT NULL,
//            `banner_sort` int(11) NOT NULL DEFAULT '0',
//                    `show_icon` char(1) NOT NULL DEFAULT 'Y',
//                    `show_desc` char(1) NOT NULL DEFAULT 'Y',
//                    `need_link` char(1) NOT NULL DEFAULT 'Y',
//                    `link_to_module` varchar(20) DEFAULT NULL,
//            `deleted` char(1) NOT NULL DEFAULT 'N',
//                    `create_time` datetime DEFAULT NULL,
//                    `create_by` bigint(20) DEFAULT NULL,
//            `update_time` datetime DEFAULT NULL,
//                    `update_by` bigint(20) DEFAULT NULL,
//            PRIMARY KEY (`id`)
}
