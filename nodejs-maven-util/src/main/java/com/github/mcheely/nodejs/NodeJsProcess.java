package com.github.mcheely.nodejs;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.String;

public class NodeJsProcess {

  public boolean executeScript(String scriptName, String[] params) throws IOException {
    File nodeJsExecutable = extractNodeBinary();

    String line = nodeJsExecutable.getAbsolutePath() + " " + scriptName;
    CommandLine cmdLine = CommandLine.parse(line);
    cmdLine.addArguments(params);
    DefaultExecutor executor = new DefaultExecutor();
    try {
      return executor.execute(cmdLine) == 0;
    } catch (ExecuteException e) {
      return e.getExitValue() == 0;
    }
  }

  private File extractNodeBinary() throws IOException {
    InputStream fileStream = getClass().getClassLoader().getResourceAsStream("nodejs/node");

    File destinationFile = File.createTempFile("nodeJsProcess", null);
    destinationFile.setExecutable(true);

    FileOutputStream fos = new FileOutputStream(destinationFile);
    IOUtils.copy(fileStream, fos);

    IOUtils.closeQuietly(fileStream);
    IOUtils.closeQuietly(fos);
    return destinationFile;
  }

}
