package constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import static constants.Models.*;

/**
 * Created by jhellinckx on 10/05/16.
 */
public class PropertiesLoader {
    public static final String PROPERTIES_FILENAME = "creatikz.properties";

    public static void load(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesStream = classLoader.getResourceAsStream(PROPERTIES_FILENAME);
        System.out.println(propertiesStream);
        Properties properties = new Properties();
        try{
            properties.load(propertiesStream);
            DEFAULT.STROKE = Integer.valueOf(properties.getProperty("DEFAULT_STROKE"));
            System.out.println(DEFAULT.STROKE);
        } catch(IOException e){
            System.err.println("Could not load properties file ! ");
        }


    }
}
