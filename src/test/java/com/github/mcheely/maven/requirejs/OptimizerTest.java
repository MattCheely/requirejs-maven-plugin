package com.github.mcheely.maven.requirejs;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.ErrorReporter;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;


/**
 * Testing Optimizer
 * @author skoranga
 *
 */
public class OptimizerTest {

	private Log log = new SystemStreamLog();

	private Optimizer optimier = null;

	private ErrorReporter reporter = null;

  private Runner runner;

	@Before
	public void setUp() throws Exception {

		optimier = new Optimizer();
		reporter = new MojoErrorReporter(log, true);
    runner = new RhinoRunner();
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testBuildConfigFull() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(loadProfile("testcase1/buildconfig1.js"), reporter, runner);
		long end = System.currentTimeMillis();

		log.debug("total time ::"+(end-start)+"msec");
	}

	@Test
	public void testBuildConfigFull2() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(loadProfile("testcase2/buildconfig2.js"), reporter, runner);
		long end = System.currentTimeMillis();

		log.debug("total time ::"+(end-start)+"msec");
	}

	@Test
	public void testBuildConfig2MainConfig() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(loadProfile("testcase2/buildconfigWithMainConfig2.js"), reporter, runner);
    long end = System.currentTimeMillis();

    log.debug("total time ::"+(end-start)+"msec");
  }

  @Test
  public void testBuildConfig2MainConfigNodeJs() throws Exception {
    String nodeCmd = NodeJsRunner.detectNodeCommand();
    assumeTrue(nodeCmd != null); //skip if no node command detected.
    long start = System.currentTimeMillis();
    optimier.optimize(loadProfile("testcase2/buildconfigWithMainConfig2.js"), reporter, new NodeJsRunner(nodeCmd));
    long end = System.currentTimeMillis();

    log.debug("total time ::"+(end-start)+"msec");
  }

  private File loadProfile(String filename) throws URISyntaxException {
    URI uri = getClass().getClassLoader().getResource(filename).toURI();
    File buildconfigFile = new File(uri);
    assertTrue(buildconfigFile.exists());
    return buildconfigFile;
  }

}
