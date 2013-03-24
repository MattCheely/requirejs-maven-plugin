package com.github.mcheely.maven.requirejs;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;

import java.io.IOException;

public class NodeJsProcess {

  public boolean executeScript(String nodeJsFile, String scriptName, String[] params) throws IOException {
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
