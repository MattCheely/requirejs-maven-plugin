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
import org.codehaus.plexus.logging.LoggerManager;
import org.codehaus.plexus.logging.console.ConsoleLoggerManager;
import org.mozilla.javascript.ErrorReporter;

/**
 * Mojo for running r.js optimization.
 *
 * @goal optimize
 * @phase process-classes
 *
 */
public class OptimizeMojo extends AbstractMojo {
    
    private static final LoggerManager CONSOLE_LOGGER_MANAGER = new ConsoleLoggerManager("WARN");
    
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
            
        } catch (MavenFilteringException e) {
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

    @SuppressWarnings("rawtypes")
    private File createBuildProfile() throws IOException, MavenFilteringException {
        if (filterConfig) {
            File filteredConfig = File.createTempFile("requirejs-maven-plugin-profile", ".js");
            mavenFileFilter.copyFile(configFile, filteredConfig, true, project, new ArrayList(), false, "UTF8", session);
            return filteredConfig;
        } else {
            return configFile;
        }
    }
}
