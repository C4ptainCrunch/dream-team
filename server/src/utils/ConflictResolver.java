package utils;

import constants.ProjectConflicts;
import models.project.Diff;
import models.project.Project;
import models.tikz.TikzGraph;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.codehaus.jparsec.error.ParserException;
import parser.DiffParser;
import parser.NodeParser;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

/**
 * This class handles conflicts between versions of a project
 */
public class ConflictResolver{
    private final static Logger logger = Log.getLogger(ConflictResolver.class);
    private Project localProject;
    private Project serverProject;
    private List<Diff> baseDiffs;
    private List<Diff> localDiffs = new ArrayList<>();
    private List<Diff> serverDiffs = new ArrayList<>();
    private List<Diff> differenceDiffsBaseServer = new ArrayList<>();
    private List<Diff> differenceDiffsBaseLocal = new ArrayList<>();


    public ConflictResolver(){
    }

    /**
     * Constructor for a Conflict Resolver between two projects
     * @param localProject the localProject to resolve conflicts
     * @param serverProject the serverProject to resolve conflicts
     */
    public ConflictResolver(Project localProject, Project serverProject){
        this.localProject = localProject;
        this.serverProject = serverProject;
    }

    /**
     * Initializes the diffs lists to compare
     * @param localDiffFile
     *          File containing the diffs of the local project
     * @param serverDiffFile
     *          File containing the diffs of the server project
     */
    public void update(File localDiffFile, File serverDiffFile){
        try{
            localDiffs = getDiffs(localDiffFile);
            serverDiffs = getDiffs(serverDiffFile);
            constructBaseDiffs();
        }catch(ClassNotFoundException | IOException e){
            logger.severe("Error while reading diff file: " + e.toString());
        }

        differenceDiffsBaseServer = getDifferenceDiffs(baseDiffs, serverDiffs);
        differenceDiffsBaseLocal = getDifferenceDiffs(baseDiffs, localDiffs);

    }

    private void update(List<Diff> localDiffs, List<Diff> serverDiffs){
        this.localDiffs = localDiffs;
        this.serverDiffs = serverDiffs;
        differenceDiffsBaseServer = getDifferenceDiffs(baseDiffs, serverDiffs);
        differenceDiffsBaseLocal = getDifferenceDiffs(baseDiffs, localDiffs);
    }

    private void constructBaseDiffs(){
        baseDiffs = new ArrayList<>();
        for(int i = 0 ; i< Math.min(localDiffs.size(),serverDiffs.size());i++){
            if(localDiffs.get(i).getPatch().equals(serverDiffs.get(i).getPatch())){
                baseDiffs.add(localDiffs.get(i));
            }else{
                break;
            }
        }
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
     *          The choice of the user whether the user wants to keep local or server version
     *          or wants to merge versions
     * @return  The merged diffs
     */
    public List<Diff> resolveDiagram(String userChoice){
        List<List<Integer>> conflictsIndexes = getConflictsIndexes(differenceDiffsBaseLocal, differenceDiffsBaseServer, false);
        List<Integer> localConflictsIndexes = conflictsIndexes.get(0);
        List<Integer> serverConflictsIndexes = conflictsIndexes.get(1);

        List<Diff> resolvedDiffs = baseDiffs;
        if(userChoice.equals(ProjectConflicts.SAVE_USER_VERSION_ONLY)){
            resolvedDiffs.addAll(differenceDiffsBaseLocal);
        }else if(userChoice.equals(ProjectConflicts.SAVE_USER_VERSION)){
            resolvedDiffs.addAll(differenceDiffsBaseLocal);
            addConflictsIndexes(differenceDiffsBaseServer, serverConflictsIndexes, resolvedDiffs);
        }else if(userChoice.equals(ProjectConflicts.SAVE_SERVER_VERSION_ONLY)){
            resolvedDiffs.addAll(differenceDiffsBaseServer);
        }else if(userChoice.equals(ProjectConflicts.SAVE_SERVER_VERSION)){
            resolvedDiffs.addAll(differenceDiffsBaseServer);
            addConflictsIndexes(differenceDiffsBaseLocal, localConflictsIndexes, resolvedDiffs);
        }else{
            //TODO: fusion
        }
        return resolvedDiffs;
    }

    /**
     * Adds conflict indexes to the resulting diff that will
     * be needed to create the new graph
     * @param diffs     The diffs list from where to get conflict diffs
     * @param conflictIndexes   List of indexes of conflict diffs
     * @param resolvedDiffs The resulting diff list
     */
    private void addConflictsIndexes(List<Diff> diffs, List<Integer> conflictIndexes, List<Diff> resolvedDiffs){
        int i = 0;
        for(Diff diff : diffs){
            if(!conflictIndexes.contains(i)){
                resolvedDiffs.add(diff);
            }
            i++;
        }
    }

    /**
     * Checks whether local and server projects have conflicts lines
     * @return Whether local and server projects have conflicts lines
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Boolean checkHasConflict() throws IOException, ClassNotFoundException {
        List<String> checkedDiagram = new ArrayList<>();
        Set<String> localDiagramNames = localProject.getDiagramNames();
        Set<String> serverDiagramNames = serverProject.getDiagramNames();
        Boolean conflicts = checkListConflicts(localDiagramNames, serverDiagramNames, checkedDiagram);
        if(!conflicts){
            conflicts = checkListConflicts(serverDiagramNames, localDiagramNames, checkedDiagram);
        }
        return conflicts;
    }

    private Boolean checkListConflicts(Set<String> diagramNamesList, Set<String> otherDiagramList, List<String> checkedDiagram) throws IOException,ClassNotFoundException{
        Boolean conflicts = false;
        for(String diagram : diagramNamesList){
            if(! checkedDiagram.contains(diagram)) {
                if (otherDiagramList.contains(diagram)) {
                    update(localProject.getDiagram(diagram).getDiffs(), serverProject.getDiagram(diagram).getDiffs());
                    String conflict = checkDiagramHasConflict();
                    if (conflict.equals(ProjectConflicts.MERGE_HAS_CONFLICTS)) {
                        conflicts = true;
                        break;
                    }
                    checkedDiagram.add(diagram);
                } else {
                    checkedDiagram.add(diagram);
                }
            }
        }
        return conflicts;
    }

    /**
     * Checks whether local and server versions have conflicts lines
     * @return Whether local and server versions have conflicts lines
     */
    public String checkDiagramHasConflict(){
        if(differenceDiffsBaseServer.size()==0){
            return ProjectConflicts.MERGE_OK;
        }else {
            if (differenceDiffsBaseLocal.size() == 0) {
                return ProjectConflicts.NO_LOCAL_MODIFICATION;
            } else {
                List<List<Integer>> hasConflict = getConflictsIndexes(differenceDiffsBaseLocal, differenceDiffsBaseServer,true);
                if(hasConflict.size() == 1){
                    return ProjectConflicts.MERGE_HAS_CONFLICTS;
                }else{
                    return ProjectConflicts.MERGE_OK;
                }
            }
        }
    }

    /**
     * Returns the Tikz code created from a list of diffs
     * @param diffs the given list of diffs
     * @return the Tikz code created
     */
    public String createTikzFromDiffs(List<Diff> diffs){
        DiffMatchPatch dmp = new DiffMatchPatch();
        List<DiffMatchPatch.Patch> patches = new LinkedList<>();
        for(Diff diff : diffs){
            patches.addAll(dmp.patchFromText(diff.getPatch()));
        }
        String original = "";
        final Object[] modified = dmp.patchApply((LinkedList<DiffMatchPatch.Patch>) patches, original);
        return (String) modified[0];
    }

    public String constructFusionDiagram(String tikzCode) {
        TikzGraph new_graph = new TikzGraph();
        try {
            NodeParser.parseDocument(new_graph).parse(tikzCode);
        } catch (ParserException e) {
            logger.info("Error during TikZ parsing : " + e.getMessage());
            return "";
        }
        return new_graph.toString();
    }

    /**
     * Gets the differences between two diffs lists
     * @param diff1 The first diff list to compare
     * @param diff2 The second diff list to compare
     * @return The diffs list containing the differences between the two diffs lists
     */
    private List<Diff> getDifferenceDiffs(List<Diff> diff1, List<Diff> diff2){
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

