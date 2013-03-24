package com.github.mcheely.maven.requirejs;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.mozilla.javascript.ErrorReporter;

import java.io.File;
import java.io.IOException;

public class NodeJsRunner implements Runner {
  private String nodeJsFile;

  public NodeJsRunner(String nodeJsFile) {
    this.nodeJsFile = nodeJsFile;
  }

  @Override
  public ExitStatus exec(File mainScript, String[] args, ErrorReporter reporter) {
    ExitStatus exitStatus = new ExitStatus();

    try {
      boolean result = executeScript(nodeJsFile, mainScript.getAbsolutePath(), args);
      exitStatus.setExitCode(result?0:1);

    } catch (IOException e) {
      exitStatus.setExitCode(1);
    }

    return exitStatus;
  }

  private boolean executeScript(String nodeJsFile, String scriptName, String[] params) throws IOException {
    String line = nodeJsFile + " " + scriptName;
    CommandLine cmdLine = CommandLine.parse(line);
    cmdLine.addArguments(params);
    DefaultExecutor executor = new DefaultExecutor();
    try {
      return executor.execute(cmdLine) == 0;
    } catch (ExecuteException e) {
      return e.getExitValue() == 0;
    }
  }
}
