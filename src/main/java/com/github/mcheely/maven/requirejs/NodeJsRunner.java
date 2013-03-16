package com.github.mcheely.maven.requirejs;

import com.github.mcheely.nodejs.NodeJsProcess;
import org.mozilla.javascript.ErrorReporter;

import java.io.File;
import java.io.IOException;

public class NodeJsRunner implements Runner {
  @Override
  public ExitStatus exec(File mainScript, String[] args, ErrorReporter reporter) {
    NodeJsProcess nodeJsProcess = new NodeJsProcess();
    ExitStatus exitStatus = new ExitStatus();

    try {
      boolean result = nodeJsProcess.executeScript(mainScript.getAbsolutePath(), args);
      exitStatus.setExitCode(result?0:1);

    } catch (IOException e) {
      exitStatus.setExitCode(1);
    }

    return exitStatus;
  }
}
