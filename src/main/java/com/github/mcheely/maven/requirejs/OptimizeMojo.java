package com.github.mcheely.maven.requirejs;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.mozilla.javascript.ErrorReporter;

/**
 * Mojo for running r.js optimization.
 *
 * @goal optimize
 * @phase process-classes
 *
 */
public class OptimizeMojo extends AbstractMojo {
    
    /**
     * Path to optimizer script.
     * 
     * @parameter
     */
    private File optimizerFile;
    
    /**
     * Path to optimizer json config.
     *
     * @parameter
     * @required
     */
    private File configFile;

    /**
     * Optimize files.
     * 
     * @throws MojoExecutionException if there is a problem optimizing files.
     */
    public void execute() throws MojoExecutionException {
        try {
            Optimizer builder = new Optimizer();
            ErrorReporter reporter = new MojoErrorReporter(getLog(), true);
            
            if (optimizerFile != null) {
                builder.optimize(createBuildProfile(), optimizerFile, reporter);
            } else {
                builder.optimize(createBuildProfile(), reporter);
            }
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

    public Log getLog() {
        return super.getLog();
    }

    @SuppressWarnings("rawtypes")
    public Map getPluginContext() {
        return super.getPluginContext();
    }

    private File createBuildProfile() throws IOException {
        return configFile;
    }
}
