package utils;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class DiffUtil {

    public static String diff(String old, String current) throws UnsupportedEncodingException {
        DiffMatchPatch diff = new DiffMatchPatch();
        String s = diff.patchToText(diff.patchMake(old, current));
        return URLDecoder.decode(s, "UTF-8");
    }
}
