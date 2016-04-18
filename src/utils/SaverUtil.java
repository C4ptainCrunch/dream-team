package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import constants.Models;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;

import constants.Utils;

public class SaverUtil {
    private static DateFormat dateFormat = new SimpleDateFormat(Utils.DATE_FORMAT);

    private SaverUtil() {
        // this was left intentionally blank
    }

    public static String makeDiff(String original, String revised) {
        DiffMatchPatch diff = new DiffMatchPatch();
        LinkedList<DiffMatchPatch.Patch> patches = diff.patchMake(original, revised);
        String s = diff.patchToText(patches);
        System.out.println("diff brut: " + s);

        System.out.println("diff propre: " + diffDecoder(s));
        return diffDecoder(s);
    }

    public static void writeDiffToFile(String original, String revised, Path path) throws IOException {
        final Date d = new Date();
        String currDate = dateFormat.format(d);
        String filename = Models.Project.DIFF_FILE;

        FileWriter f = new FileWriter(path.resolve(filename).toFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(f);
        bufferedWriter.append(currDate);
        bufferedWriter.newLine();
        bufferedWriter.append(makeDiff(original, revised));
        bufferedWriter.newLine();

        bufferedWriter.close();

    }

    private static String diffDecoder(String s) {
        StringBuilder res = new StringBuilder();
        int pos = 0;
        int sLen = s.length();
        while (pos < sLen) {
            char c = s.charAt(pos);
            if (c == '%') {
                int tmp = Integer.parseInt(s.substring(pos + 1, pos + 3), 16);
                res.append((char) tmp);
                pos += 3;
            } else {
                res.append(c);
                ++pos;
            }
        }
        return res.toString();
    }
}
