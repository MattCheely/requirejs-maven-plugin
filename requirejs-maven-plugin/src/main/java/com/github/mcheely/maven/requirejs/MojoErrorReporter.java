package com.github.mcheely.maven.requirejs;

import org.apache.maven.plugin.logging.Log;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

/**
 * JS execution error reporter for maven plugins.
 */
public class MojoErrorReporter implements ErrorReporter {
    private String defaultFilename;
    private boolean acceptWarn;
    private Log log;
    private int warningCount;
    private int errorCount;

    /**
     * Create a new error reporter.
     * @param log the log for the executing mojo
     * @param acceptWarnings whether or not to report warnings
     */
    public MojoErrorReporter(Log log, boolean acceptWarnings) {
        this.log = log;
        this.acceptWarn = acceptWarnings;
    }

    /**
     * Set the default filename to report errors against.
     * @param filename the filename to use
     */
    public void setDefaultFileName(String filename) {
        if (filename.length() == 0) {
            defaultFilename = null;
        } else {
            defaultFilename = filename;
        }
    }

    /**
     * Report an error.
     * 
     * @param message the error message
     * @param sourceName the name of the source the error occurred in
     * @param line the line number the error occurred on
     * @param lineSource the contents of the line the error occurred on
     * @param lineOffset the position in the line where the error occurred
     */
    public void error(String message, String sourceName, int line, String lineSource, int lineOffset) {
        String fullMessage = newMessage(message, sourceName, line, lineSource, lineOffset);
        log.error(fullMessage);
        errorCount++;
    }

    /**
     * Report an runtime error.
     * 
     * @param message the runtime error message
     * @param sourceName the name of the source the runtime error occurred in
     * @param line the line number the runtime error occurred on
     * @param lineSource the contents of the line the runtime error occurred on
     * @param lineOffset the position in the line where the runtime error occurred
     * @return an EvaluatorException in theory, in practice just throws one
     */
    public EvaluatorException runtimeError(String message, String sourceName, int line, String lineSource, int lineOffset) {
        error(message, sourceName, line, lineSource, lineOffset);
        throw new EvaluatorException(message, sourceName, line, lineSource, lineOffset);
    }

    /**
     * Report an warning.
     * 
     * @param message the warning message
     * @param sourceName the name of the source the warning occurred in
     * @param line the line number the warning occurred on
     * @param lineSource the contents of the line the warning occurred on
     * @param lineOffset the position in the line where the warning occurred
     */
    public void warning(String message, String sourceName, int line, String lineSource, int lineOffset) {
        if (acceptWarn) {
            String fullMessage = newMessage(message, sourceName, line, lineSource, lineOffset);
            log.warn(fullMessage);
            warningCount++;
        }
    }

    public int getErrorCnt() {
        return errorCount;
    }

    public int getWarningCnt() {
        return warningCount;
    }

    private String newMessage(String message, String sourceName, int line, String lineSource, int lineOffset) {
        StringBuilder back = new StringBuilder();
        if ((sourceName == null) || (sourceName.length() == 0)) {
            sourceName = defaultFilename;
        }
        if (sourceName != null) {
            back.append(sourceName)
                .append(":line ")
                .append(line)
                .append(":column ")
                .append(lineOffset)
                .append(':');
        }
        if ((message != null) && (message.length() != 0)) {
            back.append(message);
        } else {
            back.append("unknown error");
        }
        if ((lineSource != null) && (lineSource.length() != 0)) {
            back.append("\n\t")
                .append(lineSource);
        }
        return back.toString();
    }
}
