package common.File;

import Context.SQLExecute;
import Exceptions.FileRelativeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
}
