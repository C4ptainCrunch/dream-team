package parser;


import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;

import java.util.regex.*;


public class DiffParser {

    public static void reference(String strToParse){
        Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(strToParse);
        while(m.find()){
            if (!m.group(1).contains(",")){
                System.out.println(m.group(1));
            }
        }
    }
}
