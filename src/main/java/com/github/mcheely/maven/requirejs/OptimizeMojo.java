package com.github.mcheely.maven.requirejs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * Mojo for running r.js optimization.
 *
 * @goal optimize
 * @phase process-classes
 *
 */
public class OptimizeMojo extends AbstractMojo {
    
    /**
     * @component role="org.apache.maven.shared.filtering.MavenFileFilter"
     *            role-hint="default"
     * @required
     */
    private MavenFileFilter mavenFileFilter;
    
    /**
     * @parameter default-value="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;
    
    /**
     * @parameter expression="${session}"
     * @required
     * @readonly
     */
    protected MavenSession session; 
    
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
     * Whether or not the config file should 
     * be maven filtered for token replacement.
     * 
     * @parameter default-value=false
     */
    private boolean filterConfig;

    /**
     * Skip optimization when this parameter is true.
     *
     * @parameter default-value=false
     */
    private boolean skip;

    /**
     * Optimize files.
     * 
     * @throws MojoExecutionException if there is a problem optimizing files.
     */
    public void execute() throws MojoExecutionException {
        if (skip) {
            getLog().info("Optimization is skipped.");
            return;
        }

        try {
            Optimizer builder = new Optimizer();
            ErrorReporter reporter = new MojoErrorReporter(getLog(), true);
            
            if (optimizerFile != null) {
                builder.optimize(createBuildProfile(), optimizerFile, reporter);
            } else {
                builder.optimize(createBuildProfile(), reporter);
            }
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to read r.js", e);
        } catch (EvaluatorException e) {
            throw new MojoExecutionException("Failed to execute r.js", e);
        }

    }

    public Log getLog() {
        return super.getLog();
    }

    @SuppressWarnings("rawtypes")
    public Map getPluginContext() {
        return super.getPluginContext();
    }

    @SuppressWarnings("rawtypes")
    private File createBuildProfile() throws MojoExecutionException {
        if (filterConfig) {
            File filteredConfig;
            
            try {
                filteredConfig = File.createTempFile("requirejs-maven-plugin-profile", ".js");
                mavenFileFilter.copyFile(configFile, filteredConfig, true, project, new ArrayList(), true, "UTF8", session);
            } catch (IOException e) {
                throw new MojoExecutionException("Error creating temp copy of config.", e);
            } catch (MavenFilteringException e) {
                throw new MojoExecutionException("Error filtering config file.", e);
            }
            
            return filteredConfig;
        } else {
            return configFile;
        }
    }
}
