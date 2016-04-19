package utils;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DiffUtil {

    public static String diff(String old, String current) throws UnsupportedEncodingException {
        DiffMatchPatch diff = new DiffMatchPatch();
        return diff.patchToText(diff.patchMake(old, current));
    }
}
