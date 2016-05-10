package misc.utils;

import models.project.Project;
import utils.DiffUtil;
import utils.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

public class ConflictResolver{
    private final static Logger logger = Log.getLogger(ConflictResolver.class);
    String result = "";
    String printResult = "";
    public ConflictResolver(){

        File diffFile1 = new File("/home/end3rs/Desktop/diffs");
        File diffFile2 = new File("/home/end3rs/Desktop/p3/diffs");
        String diff1="";
        String diff2="";
        try{
            diff1 =new String( Files.readAllBytes(diffFile1.toPath()));
            diff2 =new String( Files.readAllBytes(diffFile2.toPath()));

        }catch(IOException e){

        }
        try {
            result = DiffUtil.diff(diff1, diff2);
            for (String s : result.split(System.getProperty("line.separator"))) {
                //printResult += s + "\n";
                System.out.println(s);

            }
            //System.out.println(printResult);
        }catch(UnsupportedEncodingException e){

        }

    }

/*
    public Boolean conflictExists(){
        Boolean res = false;
        try{
            String tikzLocal = localProject.getDiskTikz();
            String tikzDistant = distantProject.getDiskTikz();
            res = !(tikzLocal == tikzDistant);
        }catch(IOException e){
            logger.fine("Warning while reading the save file : " + e.toString());
        }
        return res;
    }
    */
}

