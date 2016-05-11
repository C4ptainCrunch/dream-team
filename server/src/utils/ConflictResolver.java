package utils;

import models.project.Diff;
import parser.DiffParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class handles conflicts between versions of a project
 */
public class ConflictResolver{
    private final static Logger logger = Log.getLogger(ConflictResolver.class);
    private List<Diff> baseDiffs = new ArrayList<>();
    private List<Diff> localDiffs = new ArrayList<>();
    private List<Diff> serverDiffs = new ArrayList<>();
    private List<Diff> differenceDiffsBaseServer = new ArrayList<>();
    private List<Diff> differenceDiffsBaseLocal = new ArrayList<>();


    public ConflictResolver(){

    }

    /**
     * Initializes the diffs lists to compare
     * @param baseDiffFile
     *          File containing the diffs of the original project
     * @param localDiffFile
     *          File containing the diffs of the local project
     * @param serverDiffFile
     *          File containing the diffs of the server project
     */
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

    /**
     * Resolves conflicts between the local and server diffs given the choice of the user
     * The choice can either be:
     * - saveUserVersion : Keeps modifications of the local diffs and server diffs but rejects
     * diffs from server diffs that causes conflicts
     * conflicts from server diffs
     * - saveServerVersion : Keeps modifications of the server diffs and local diffs but rejects
     * diffs from local diffs that causes conflicts
     *
     * @param userChoice
     *          The choice of the user wheter the user wants to keep local or server version
     *          or wants to merge versions
     * @return  The merged diffs
     */
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


    /**
     * Checks wheter local and server versions have conflicts lines
     * @return Wheter local and server versions have conflicts lines
     */
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

    /**
     * Gets the differences between two diffs lists
     * @param diff1 The first diff list to compare
     * @param diff2 The second diff list to compare
     * @return The diffs list containing the differences between the two diffs lists
     */
    public List<Diff> getDifferenceDiffs(List<Diff> diff1, List<Diff> diff2){
        if(diff1.size() == diff2.size()) {
            return new ArrayList<Diff>();
        }else if (diff1.size() > diff2.size()){
            return diff1.subList(diff2.size(), diff1.size());
        }
        return diff2.subList(diff1.size(), diff2.size());
    }

    /**
     * Initialiazes two lists of indexes, one for the local diffs,
     * one for the server diffs. Those indexes indicates which
     * diffs (for local and server diffs) contain conflict
     * elements (ie. same reference node) if the checkOnly
     * is at False.
     * If the checkOnly option is at True, this method stops
     * at the first conflict occurence encountered and return list
     * of size one.
     * @param local    diffs list of the local version
     * @param server    diffs list of the server version
     * @param checkOnly boolean option to stop at first conflict encountered or not
     * @return List containing lists of indexes for each diffs list passed
     */
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

    /**
     * Checks whether the reference found in the local
     * version is found in the server version
     * @param server diffs list of the server
     * @param localReference the reference found in the lcal version
     * @param conflictsReferences Hashset containing the references that cause conflict
     * @param serverConflictsIndexes List containing the indexes of the diffs of the server version that causes conflict
     * @param localConflictsIndexes List containing the indexes of the diffs of the local version that causes conflict
     * @param i index of the diffs of the local version
     * @param checkedReferences  Hashset of references already checked -> no need to check again
     * @param checkOnly Boolean option to stop at first conflict encountered or not
     * @return Whether the given reference from local diff causes conflicts with server diffs
     */
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

    /**
     * Initializes a list of diffs from a diff file
     * @param f The diff file
     * @return The diffs list
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private List<Diff> getDiffs(File f) throws IOException, ClassNotFoundException {
        FileInputStream fs = new FileInputStream(f);
        ObjectInputStream os = new ObjectInputStream(fs);
        List<Diff> diffs = (List<Diff>) os.readObject();
        os.close();
        fs.close();

        return diffs;
    }
}

