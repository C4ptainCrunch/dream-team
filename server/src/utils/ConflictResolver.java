package utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import models.project.Diff;
import parser.DiffParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class ConflictResolver{
    private final static Logger logger = Log.getLogger(ConflictResolver.class);
    private List<Diff> baseDiffs = new ArrayList<>();
    private List<Diff> localDiffs = new ArrayList<>();
    private List<Diff> serverDiffs = new ArrayList<>();
    private List<Diff> differenceDiffsBaseServer = new ArrayList<>();
    private List<Diff> differenceDiffsBaseLocal = new ArrayList<>();


    public ConflictResolver(){

    }

    public void update(File baseDiffFile, File localDiffFile, File serverDiffFile){
        try{
            baseDiffs = getDiffs(baseDiffFile);
            localDiffs = getDiffs(localDiffFile);
            serverDiffs = getDiffs(serverDiffFile);
        }catch(ClassNotFoundException | IOException e){
            logger.severe("Error while reading diff file: " + e.toString());
        }

        differenceDiffsBaseServer = getDifferenceDiffs(baseDiffs, serverDiffs);
        differenceDiffsBaseLocal = getDifferenceDiffs(baseDiffs, localDiffs);
    }

    public List<Diff> resolve(String userChoice){
        List<List<Integer>> conflictsIndexes = getConflictsIndexes(differenceDiffsBaseLocal, differenceDiffsBaseServer, false);
        List<Integer> localConflictsIndexes = conflictsIndexes.get(0);
        List<Integer> serverConflictsIndexes = conflictsIndexes.get(1);

        List<Diff> resolvedDiffs = baseDiffs;
        if(userChoice.equals("saveUserVersion")){
            resolvedDiffs.addAll(localDiffs);
            int i = 0;
            for(Diff serverDiff: serverDiffs){
                if(!serverConflictsIndexes.contains(i)){
                    resolvedDiffs.add(serverDiff);
                }
                i++;
            }
        }else if(userChoice.equals("saveServerVersion")){
            resolvedDiffs.addAll(serverDiffs);
            int i = 0;
            for (Diff localDiff : localDiffs){
                if(!localConflictsIndexes.contains(i)){
                    resolvedDiffs.add(localDiff);
                }
                i++;
            }

        }else{
            //TODO: merge
        }
        return resolvedDiffs;
    }



    public Integer checkHasConflict(){
        if(differenceDiffsBaseServer.size()==0){
            System.out.println("PUSH OK : NO MODIFICATIONS DONE ON SERVER");
            return 0;
        }else {
            if (differenceDiffsBaseLocal.size() == 0) {
                System.out.println("NO MODIFICATION DONE LOCALLY");
                return 2; //Keep server version
            } else {
                List<List<Integer>> hasConflict = getConflictsIndexes(differenceDiffsBaseLocal, differenceDiffsBaseServer,true);
                if(hasConflict.size() == 1){
                    return 1;
                }else{
                    return 0;
                }
            }
        }

    }

    public List<Diff> getDifferenceDiffs(List<Diff> diff1, List<Diff> diff2){
        if(diff1.size() == diff2.size()) {
            return new ArrayList<Diff>();
        }else if (diff1.size() > diff2.size()){
            return diff1.subList(diff2.size(), diff1.size());
        }
        return diff2.subList(diff1.size(), diff2.size());
    }

    private List<List<Integer>> getConflictsIndexes(List<Diff> local, List<Diff> server, Boolean checkOnly) {
        List<Integer> localConflictsIndexes = new ArrayList<>();
        List<Integer> serverConflictsIndexes = new ArrayList<>();
        HashSet<String> conflictsReferences = new HashSet<>();
        HashSet<String> checkedReferences = new HashSet<>();

        int i=0;
        for (Diff localDiff : local) {
            List<String> localReferences = DiffParser.reference(localDiff.getPatch());
            for(String localReference : localReferences){
                if (! checkedReferences.contains(localReference)){
                    Boolean hasConflict = checkIfReferenceIsConflict(server, localReference, conflictsReferences, serverConflictsIndexes, localConflictsIndexes, i, checkedReferences, checkOnly);
                    if(checkOnly && hasConflict){
                        List<List<Integer>> emptyList = new ArrayList<>();
                        emptyList.add(new ArrayList<>());
                        return emptyList;
                    }
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

    private Boolean checkIfReferenceIsConflict(List<Diff> server, String localReference, HashSet<String> conflictsReferences, List<Integer> serverConflictsIndexes, List<Integer> localConflictsIndexes, int i, HashSet<String> checkedReferences, Boolean checkOnly){
        int j=0;
        for (Diff serverDiff : server){
            List<String> serverReferences = DiffParser.reference(serverDiff.getPatch());
            if(serverReferences.contains(localReference)){
                if(checkOnly){
                    return true;
                }
                conflictsReferences.add(localReference);
                serverConflictsIndexes.add(j);
                if (!localConflictsIndexes.contains(i)){
                    localConflictsIndexes.add(i);
                }
            }
            j++;
        }
        checkedReferences.add(localReference);
        return false;
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

