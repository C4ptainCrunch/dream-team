package misc.utils;

import models.project.Diff;
import models.project.Project;
import parser.DiffParser;
import utils.DiffUtil;
import utils.Log;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ConflictResolver{
    private final static Logger logger = Log.getLogger(ConflictResolver.class);
    public ConflictResolver(){

        File baseDiffFile = new File("/home/mrmmtb/diffs");
        File localDiffFile = new File("/home/mrmmtb/p1/diffs");
        File serverDiffFile = new File("/home/mrmmtb/Dropbox/diffs");
        List<Diff> baseDiffs= new ArrayList<>();
        List<Diff> localDiffs= new ArrayList<>();
        List<Diff> serverDiffs = new ArrayList<>();
        try{
            baseDiffs = getDiffs(baseDiffFile);
            localDiffs = getDiffs(localDiffFile);
            serverDiffs = getDiffs(serverDiffFile);
        }catch(ClassNotFoundException | IOException e){
            //TODO:Add logger
        }

        List<Diff> differenceDiffsBaseServer = getDifferenceDiffs(baseDiffs, serverDiffs);
        if(differenceDiffsBaseServer.size()==0){
            System.out.println("PUSH OK : NO MODIFICATIONS DONE ON SERVER");
        }else {
            List<Diff> differenceDiffsBaseLocal = getDifferenceDiffs(baseDiffs, localDiffs);
            if (differenceDiffsBaseLocal.size() == 0) {
                System.out.println("NO MODIFICATION DONE LOCALLY");
            } else {
                List<List<Integer>> conflictsIndexes = getConflictsIndexes(differenceDiffsBaseLocal, differenceDiffsBaseServer);
                List<Integer> localConflictsIndexes = conflictsIndexes.get(0);
                List<Integer> serverConflictsIndexes = conflictsIndexes.get(1);
                if(localConflictsIndexes.size()!=0){
                    System.out.println("Local:");
                    for(int i : localConflictsIndexes){
                        System.out.println(i);
                    }
                    System.out.println("Server:");
                    for(int j : serverConflictsIndexes){
                        System.out.println(j);
                    }
                }
            }
        }
    }

    private List<Diff> getDifferenceDiffs(List<Diff> diff1, List<Diff> diff2){
        if(diff1.size() == diff2.size()) {
            return new ArrayList<Diff>();
        }else if (diff1.size() > diff2.size()){
            return diff1.subList(diff2.size(), diff1.size());
        }
        return diff2.subList(diff1.size(), diff2.size());
    }

    private List<List<Integer>> getConflictsIndexes(List<Diff> local, List<Diff> server) {
        List<Integer> localConflictsIndexes = new ArrayList<>();
        List<Integer> serverConflictsIndexes = new ArrayList<>();
        HashSet<String> conflictsReferences = new HashSet<>();
        HashSet<String> checkedReferences = new HashSet<>();

        int i=0;
        for (Diff localDiff : local) {
            List<String> localReferences = DiffParser.reference(localDiff.getPatch());
            for(String localReference : localReferences){
                if (! checkedReferences.contains(localReference)){
                    checkIfReferenceIsConflict(server, localReference, conflictsReferences, serverConflictsIndexes, localConflictsIndexes, i, checkedReferences);
                }else{
                    if (conflictsReferences.contains(localReference)){
                        localConflictsIndexes.add(i);
                    }
                }
            }
            i++;
        }
        List<List<Integer>> res = new ArrayList<>();
        res.add(localConflictsIndexes);
        res.add(serverConflictsIndexes);
        return res;
    }

    private void checkIfReferenceIsConflict(List<Diff> server, String localReference, HashSet<String> conflictsReferences, List<Integer> serverConflictsIndexes, List<Integer> localConflictsIndexes, int i, HashSet<String> checkedReferences){
        int j=0;
        for (Diff serverDiff : server){
            List<String> serverReferences = DiffParser.reference(serverDiff.getPatch());
            if(serverReferences.contains(localReference)){
                conflictsReferences.add(localReference);
                serverConflictsIndexes.add(j);
                if (!localConflictsIndexes.contains(i)){
                    localConflictsIndexes.add(i);
                }
            }
            j++;
        }
        checkedReferences.add(localReference);
    }

    private List<Diff> getDiffs(File f) throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(f);
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> diffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

        return diffs;
    }
}

