# RequireJS maven plugin

Builds javascript applications using the Asynchronous Module Definition (AMD)
pattern to define classes and dependencies between them. See:

https://github.com/amdjs/amdjs-api/wiki/AMD

## Features

**simple**

The plugin has a very simple design. Just provide a json confg file for the
optimization process as documented at http://requirejs.org/docs/optimization.html#wholeproject

**forward/backward/sideways compatible**

We'll make an effort to keep the r.js version embedded in the plugin up to date, but if
we've fallen behind, or you need to work with an older or custom version for some reason, it's as
simple as specifying the path to the script you want to use.

**maven filtering**

You can optionally tell the plugin to filter your config file with the standard maven filters
giving you the ability to use properties defined in your pom file or by maven itself (but see
the note below about relative paths).

## Usage

Just add the plugin to your pom:

    <plugins>
      <plugin>
        <groupId>com.github.mcheely</groupId>
        <artifactId>requirejs-maven-plugin</artifactId>
        <version>1.0.0-SNAPSHOT</version>
        <executions>
          <execution>
            <goals>
              <goal>optimize</goal>
            </goals>
          </execution>
        </executions>
        <configuration>
            <!-- path to optimizer json config file -->
            <configFile>
                ${basedir}/src/main/config/buildconfig.js
            </configFile>
            <!-- optional path to optimizer executable -->
            <optimizerFile>
                ${basedir}/src/main/scripts/r.js
            </optimizerFile>
            <!-- whether or not to process config with maven filters -->
            <filterConfig>
                true
            </filterConfig>
            <!-- Skip requirejs optimization if true -->
            <skip>
                false
            </skip>
        </configuration>
      </plugin>
    </plugins>

### Goal: optimize

```mvn requirejs:optimize```

Uses the r.js optimizer to aggregate and minify your project. Dependencies should be defined using
the Asynchronous Module Definition pattern, see the RequireJS documentation at:

http://requirejs.org

The "optimize" goal is by default attached to the "process-classes" maven phase. It will go through
all the js modules you have listed in your project configuration file, interpret the AMD dependencies
each file has, aggregate and minify that entire dependency tree, and put the resulting minified filed in
your output directory.

Optimization is configured using a json project configuration file. For details of the options available,
see the RequireJS optimization documentation at:

http://requirejs.org/docs/optimization.html#wholeproject

### Plugin Options

**configFile**

The path to the config file that will be passed to the r.js optimizer.

**optimizerFile**

The path to the optimizer script (r.js) that will be run to optimize your app. If not provided, the default version
from the classpath will be used.

**filterConfig**

Boolean option to indicate whether or not to run the config file through maven filters to replace tokens
like ${basedir} (defaults to false)

*Important Note:* if you enable filterConfig, be sure that the 'appDir' and 'dir' options in your config
use absolute paths. The easiest way to do that is to use the maven path variables like ${basedir} to prefix
your paths. Otherwise, the build won't find your files, as the filtered version of the config file is created in
a temporary location outside of the project.

**skip**

If skip is set to true, optimization will be skipped. This may be useful for reducing build time if optimization is not needed.
It can also be set via the command line with ```-Drequirejs.optimize.skip=true```.

## Thanks

requirejs-maven-plugin is available on github because my employer, lulu.com, is great about letting me
share the work I do for them.

This project is originally based on Jacob Hansson's brew plugin at:
https://github.com/jakewins/brew
