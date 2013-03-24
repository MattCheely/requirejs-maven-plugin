package com.github.mcheely.maven.requirejs;

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
    NodeJsProcess nodeJsProcess = new NodeJsProcess();
    ExitStatus exitStatus = new ExitStatus();

    try {
      boolean result = nodeJsProcess.executeScript(nodeJsFile, mainScript.getAbsolutePath(), args);
      exitStatus.setExitCode(result?0:1);

    } catch (IOException e) {
      exitStatus.setExitCode(1);
    }

    return exitStatus;
  }
}
