package utils;

import java.io.UnsupportedEncodingException;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

public class DiffUtil {

    /**
     * create a diff between two string
     * @param old previous string
     * @param current current string
     * @return a string representation of a diff
     * @throws UnsupportedEncodingException
     */
    public static String diff(String old, String current) throws UnsupportedEncodingException {
        DiffMatchPatch diff = new DiffMatchPatch();
        return diff.patchToText(diff.patchMake(old, current));
    }

    public static String diffForUndo(String old, String current) throws UnsupportedEncodingException {
        DiffMatchPatch diff = new DiffMatchPatch();
        return diff.patchToText(diff.patchMake(current, old));
    }
}
