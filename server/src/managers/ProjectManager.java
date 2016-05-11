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
}
