package utils;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.*;
import java.io.*;

import javax.swing.*;

import models.tikz.TikzGraph;

public final class PdfRenderer {
    private PdfRenderer() {
    }

    private static void toSourceFile(File filename, TikzGraph graph) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(filename, "utf-8");
        writer.print(LaTeXBuilder.toLaTeX(graph));
        writer.close();
    }

    public static void compileAndOpen(File pdfTarget, TikzGraph graph) throws PdfCompilationError {
        File buildDir = new File(pdfTarget.getParent(), ".build/");
        File source = new File(buildDir, "source.tex");
        File pdf = new File(buildDir, "source.pdf");

        if (!buildDir.exists()) {
            try {
                buildDir.mkdir();
            } catch (SecurityException e) {
                throw new PdfCompilationError("Could not create build dir", e);
            }
        }

        try {
            toSourceFile(source, graph);
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new PdfCompilationError("Could not write .tex to file", e);
        }

        Process p;
        try {
            String[] tex_compile_command = { "pdflatex", "-output-directory=" + buildDir.getAbsolutePath(), source.getAbsolutePath() };
            p = Runtime.getRuntime().exec(tex_compile_command);

        } catch (IOException e) {
            throw new PdfCompilationError("pdflatex failed", e);
        }

        SwingUtilities.invokeLater(() -> {
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                // we do not care if we were interrupted : do nothing
            }

            pdf.renameTo(pdfTarget);

            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(pdfTarget);
                } catch (IOException ex) {
                    // if opening the file failed, just silently skip
                    // because it is not important, the file has already been
                    // compiled
                }
            } else {
                showMessageDialog(null, "Compilation ended : " + pdfTarget.toString());
            }
        });
    }
}
