package managers;

import models.project.Diff;
import models.tikz.TikzGraph;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import utils.ConflictResolver;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProjectManager {
    ConflictResolver conflictResolver;

    ProjectManager(){
        conflictResolver = new ConflictResolver();
    }

    /*public String createTikzFromDiffs(List<Diff> diffs){
        DiffMatchPatch undo = new DiffMatchPatch();
        List<DiffMatchPatch.Patch> patches = new ArrayList<>();
        for(Diff diff : diffs){
            patches = undo.patchFromText(diff.getPatch());
        }
        String original = "";
        final Object[] modified = undo.patchApply((LinkedList<DiffMatchPatch.Patch>) patches, original);
        Object[] x = (Object[]) modified[1];
        return (String) modified[0];
    }*/

    public ConflictResolver getConflictResolver() {
        return conflictResolver;
    }

    public String createTikzFromDiffs(Diff diffs){
        DiffMatchPatch undo = new DiffMatchPatch();
        List<DiffMatchPatch.Patch> patches = undo.patchFromText(diffs.getPatch());
        for (DiffMatchPatch.Patch pa : patches){
            System.out.println(pa.toString());
        }
        TikzGraph graph = new TikzGraph();
        String original = graph.toString();
        final Object[] modified = undo.patchApply((LinkedList<DiffMatchPatch.Patch>) patches, original);
        return (String) modified[0];
    }
}
