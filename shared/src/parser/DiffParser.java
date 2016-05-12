package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;


public class DiffParser {

    public static List<String> reference(String strToParse){
        List<String> references = new ArrayList<>();
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(strToParse);
        while(m.find()){
            if (!m.group(1).contains(",")){
                references.add(m.group(1));
            }
        }
        return references;
    }
}
