package com.github.mcheely.nodejs;

import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NodeJsProcessTest {

  @Test
  public void testProcessExecution() throws IOException {
    File scriptFile = File.createTempFile("nodejs-test-script", null);
    IOUtils.copy(getClass().getClassLoader().getResourceAsStream("test1.js"), new FileOutputStream(scriptFile));

    NodeJsProcess process = new NodeJsProcess();
    Assert.assertTrue(process.executeScript(scriptFile.getAbsolutePath(), null));
  }

  @Test
  public void testProcessExecutionWithError() throws IOException {
    File scriptFile = File.createTempFile("nodejs-test-script", null);
    IOUtils.copy(getClass().getClassLoader().getResourceAsStream("testError.js"), new FileOutputStream(scriptFile));

    NodeJsProcess process = new NodeJsProcess();
    Assert.assertFalse(process.executeScript(scriptFile.getAbsolutePath(), null));
  }
}
