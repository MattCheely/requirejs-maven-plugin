package com.github.mcheely.maven.requirejs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.plexus.util.IOUtil;
import org.mozilla.javascript.ErrorReporter;

/**
 * Optimizes js files.
 */
public class Optimizer {

    private static final String CLASSPATH_R_JS = "/r.js";

    /**
     * Optimize using the built-in version of r.js.
     * 
     * @param buildProfile file containing optimizer build profile configuration
     * @param reporter error reporter
     * @throws IOException if there is a problem reading/writing optimization files
     */
    public void optimize(File buildProfile, ErrorReporter reporter) throws IOException {
        File optimizerFile = getClasspathOptimizerFile();
        optimize(buildProfile, optimizerFile, reporter);
    }

    /**
     * Optimize using an external version of r.js.
     * 
     * @param buildProfile file containing optimizer build profile configuration
     * @param optimizerFile file containing optimizer library
     * @param reporter error reporter
     * @throws IOException if there is a problem reading/writing optimization files
     */
    public void optimize(File buildProfile, File optimizerFile, ErrorReporter reporter) throws IOException {
        
        File[] includes = new File[0];

        String[] args = new String[2];
        args[0] = "-o";
        args[1] = buildProfile.getAbsolutePath();

        Map<String, Object> globalVariables = new HashMap<String, Object>();
        
        RhinoRunner.exec(includes, optimizerFile, args, globalVariables, reporter);
    }

    private File getClasspathOptimizerFile() throws IOException {
        File optimizerFile = File.createTempFile("build", "js");
        optimizerFile.deleteOnExit();
        FileOutputStream out = null;
        InputStream in = null;
        try {
            in = getClass().getResourceAsStream(CLASSPATH_R_JS);
            out = new FileOutputStream(optimizerFile);
            IOUtil.copy(in, out);
        } finally {
            IOUtil.close(in);
            IOUtil.close(out);
        }
        
        return optimizerFile;
    }

}
