package utils;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.io.*;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import constants.Utils;


public class SaverFactory {
    private static DateFormat dateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);


    public SaverFactory(){

    }

    public String makeDiff(String original,String revised){
        DiffMatchPatch diff = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Patch> patches = diff.patchMake(original,revised);
        String s = diff.patchToText(patches);
        System.out.println("diff brut: "+s);

        System.out.println("diff propre: "+ diffDecoder(s));
        return diffDecoder(s);
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
            bufferedWriter.newLine();

            bufferedWriter.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    private String diffDecoder(String s){
        StringBuilder res = new StringBuilder();
        int pos = 0, sLen = s.length();
        while (pos < sLen) {
            char c = s.charAt(pos);
            if (c == '%') {
                int tmp = Integer.parseInt(s.substring(pos+1, pos+3), 16);
                res.append((char) tmp);
                pos += 3;
            } else {
                res.append(c);
                ++pos;
            }
        }
        return res.toString();
    }
}
