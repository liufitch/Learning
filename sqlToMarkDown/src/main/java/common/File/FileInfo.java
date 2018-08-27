package common.File;

import Context.SQLExecute;
import Exceptions.FileRelativeException;
import entity.TableEntity;

import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class FileInfo {
    private  String fileName = null;
    private static FileInfo  fileInfo = null;

    public static FileInfo getInstance(){
        if(fileInfo == null){
            fileInfo = new FileInfo();
        }
        return fileInfo;
    }

    private FileInfo(){
        this.fileName = "dataSource.properties";
    }


    public void createFile (String pathName) throws FileRelativeException {
        Path path = Paths.get(pathName);
        try {
            if(!Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})){
                Files.createFile(path);
            }else{
                throw new FileRelativeException("the file existed.");
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public String readPropertiesFilePathName(){
        Properties file = new Properties();
        InputStream input = null;
        String pathName = null;
        try{

            //load a properties file from  classpath;

            input = SQLExecute.class.getClassLoader().getResourceAsStream(fileName);
            if(input == null){
                throw  new FileRelativeException("cant find the property file in the classpath");
            }

            file.load(input);
            pathName = file.getProperty("pathName");

        }catch (FileRelativeException fileException){
            fileException.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if( input !=null){
                try{
                    input.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return pathName;
        }
    }


    public Map readPropertiesFile(){
        Properties file = new Properties();
        InputStream input = null;
        Map map = new HashMap();
        try{

            //load a properties file from  classpath;

            input = SQLExecute.class.getClassLoader().getResourceAsStream(fileName);
            if(input == null){
                throw  new FileRelativeException("cant find the property file in the classpath");
            }

            file.load(input);
            map.put("url", file.getProperty("dataSourceUrl"));
            map.put("user", file.getProperty("user"));
            map.put("password", file.getProperty("password"));
            map.put("driver", file.getProperty("driver"));

        }catch (FileRelativeException fileException){
            fileException.printStackTrace();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }finally {
            if( input !=null){
                try{
                    input.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            return map;
        }
    }

    public void writeFileAppend(List<String> sql, String pathName){
        try{
            Path path = Paths.get(pathName);
            Files.write(path, sql, StandardOpenOption.APPEND);

        }catch (NoSuchFileException e){
            try{
                createFile(pathName);
                Path path = Paths.get(pathName);
                Files.write(path, sql, StandardOpenOption.APPEND);
            }catch (FileRelativeException | IOException e1){
                e.printStackTrace();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void serializeTableEntity(List<TableEntity> list,String pathName, String tableName){
        FileOutputStream fos = null;
        PrintStream out1 = null;
        try{
            File file = new File(pathName);
            fos = new FileOutputStream(file, true);
            // 创建FileOutputStream对应的PrintStream，方便操作。PrintStream的写入接口更便利
            out1 = new PrintStream(fos);

            out1.print("##" + tableName +"\r\n");
            for(int i=0;i<list.size();i++){

                out1.print( list.get(i).toString()+"\r\n");

            }
            out1.close();
        }catch ( IOException e){
            e.printStackTrace();
        }finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
