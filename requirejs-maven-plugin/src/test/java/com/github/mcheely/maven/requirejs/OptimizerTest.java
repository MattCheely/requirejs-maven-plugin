package com.github.mcheely.maven.requirejs;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;

import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mozilla.javascript.ErrorReporter;


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
	
	private File createBuildProfileForUseCase1() throws Exception {
      URI uri = getClass().getClassLoader().getResource("testcase1/buildconfig1.js").toURI();
			File buildconfigFile = new File(uri);
			assertTrue(buildconfigFile.exists());
			return buildconfigFile ;
    }
	
	@Test
	public void testBuildConfigFull() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(createBuildProfileForUseCase1(), reporter, runner);
		long end = System.currentTimeMillis();
		
		log.debug("total time ::"+(end-start)+"msec");
	}

	private File createBuildProfileForUseCase2() throws Exception {
        
        URI uri = getClass().getClassLoader().getResource("testcase2/buildconfig2.js").toURI();
		File buildconfigFile = new File(uri);
		assertTrue(buildconfigFile.exists());
		return buildconfigFile ;
}
	
	@Test
	public void testBuildConfigFull2() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(createBuildProfileForUseCase2(), reporter, runner);
		long end = System.currentTimeMillis();
		
		log.debug("total time ::"+(end-start)+"msec");
	}
	
	private File createBuildProfileForUseCase2MainConfig() throws Exception {
        
        URI uri = getClass().getClassLoader().getResource("testcase2/buildconfigWithMainConfig2.js").toURI();
		File buildconfigFile = new File(uri);
		assertTrue(buildconfigFile.exists());
		return buildconfigFile ;
}
	
	@Test
	public void testBuildConfig2MainConfig() throws Exception {
		long start = System.currentTimeMillis();
		optimier.optimize(createBuildProfileForUseCase2MainConfig(), reporter, runner);
    long end = System.currentTimeMillis();

    log.debug("total time ::"+(end-start)+"msec");
  }

  @Test
  public void testBuildConfig2MainConfigNodeJs() throws Exception {
    long start = System.currentTimeMillis();
    optimier.optimize(createBuildProfileForUseCase2MainConfig(), reporter, new NodeJsRunner());
    long end = System.currentTimeMillis();

    log.debug("total time ::"+(end-start)+"msec");
  }

}
