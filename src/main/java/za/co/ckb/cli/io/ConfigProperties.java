package za.co.ckb.cli.io;

import java.io.*;
import java.util.Properties;


public final class ConfigProperties {

    public enum PROPERTY_NAMES{
        PROPERTY_1("property.one","Property one description"),
        PROPERTY_2("property.two","Property two description"),
        
        ;

        private String text;
        private String description;

        PROPERTY_NAMES(String textParam, String descriptionParam){
            this.text = textParam;
            this.description = descriptionParam;
        }

        public String getText(){
            return this.text;
        }


        public String getDescription(){
            return this.description;
        }
    }

    private static ConfigProperties instance;
    private String cliPropertiesFile = System.getProperty("user.home")+"/mycli.properties";


    public static synchronized ConfigProperties getInstance(){
        if (instance == null){
            instance = new ConfigProperties();
        }
        return instance;
    }

    public static String getProperty(PROPERTY_NAMES propertyName){
        return getInstance().getProperties().getProperty(propertyName.getText(),"UNSET").toString();
    }

    public Properties getProperties(){

        createFile();

        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(cliPropertiesFile)) {
            prop.load(input);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return prop;
    }

    private File createFile() {
        File file = new File(cliPropertiesFile);
        try {
            if (!file.exists()) {
                System.err.println("File created : "+file.getAbsolutePath());
                file.createNewFile();
            }
        } catch (IOException io){
            io.printStackTrace();
        }
        return file;
    }

    public void writeProperties(Properties prop){

        File file = createFile();
        file.renameTo(new File(cliPropertiesFile +"_"+System.currentTimeMillis()+".bak"));

        try (OutputStream output = new FileOutputStream(cliPropertiesFile)) {
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
