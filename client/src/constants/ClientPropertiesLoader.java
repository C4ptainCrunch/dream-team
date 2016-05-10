package constants;

import java.util.Properties;

/**
 * Created by jhellinckx on 10/05/16.
 */
public class ClientPropertiesLoader extends PropertiesLoader {
    public static void loadAll(){
        PropertiesLoader.loadAll();
        loadErrorsProperties();
        loadGUIProperties();
        loadWarningProperties();
    }

    public static void loadErrorsProperties(){

    }

    public static void loadGUIProperties(){

    }

    public static void loadWarningProperties(){

    }
}

class ErrorsPropertiesReader implements PropertiesReader{
    @Override
    public void read(Properties properties){

    }
}

class GUIPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties) {

    }
}

class WarningPropertiesReader implements PropertiesReader{

    @Override
    public void read(Properties properties) {

    }
}
