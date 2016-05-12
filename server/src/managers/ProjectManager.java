package managers;

import models.databaseModels.User;
import models.project.Diff;
import models.project.Project;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import utils.ConflictResolver;

import java.util.LinkedList;
import java.util.List;

/**
 * This class handles the server-side handling of the projects (database link, conflict link, ...)
 */
public class ProjectManager {
    ConflictResolver conflictResolver;
    Project project;
    User user;

    /**
     * Creates a new ProjectManager & creates a new ConflictsResolver
     */
    ProjectManager(){
        conflictResolver = new ConflictResolver();
    }

    /**
     * Getter for the ConflictResolver, used for tests
     * @return the ConflictResolver
     */
    public ConflictResolver getConflictResolver() {
        return conflictResolver;
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

    public void update(Project project, User user){
        this.project = project;
        this.user = user;
    }


}
