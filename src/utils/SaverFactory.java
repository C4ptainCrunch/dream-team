package utils;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


public class SaverFactory {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    public SaverFactory(){

    }

    public String makeDiff(String original,String revised){
        DiffMatchPatch diff = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Patch> patches = diff.patchMake(original,revised);
        System.out.println("Diff: "+diff.patchToText(patches));
        return diff.patchToText(patches);
    }

    public void writeToFile(String original,String revised, String path){
        final Date d = new Date();
        String currDate = dateFormat.format(d);
        String filename = "diffs";
        try{
            FileWriter f = new FileWriter(path+"/"+filename, true);
            BufferedWriter bufferedWriter = new BufferedWriter(f);
            bufferedWriter.append(currDate);
            bufferedWriter.newLine();
            bufferedWriter.append(makeDiff(original, revised));
            System.out.println("original: "+original);
            System.out.println("revised: "+revised);
            bufferedWriter.newLine();

            bufferedWriter.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }


}
