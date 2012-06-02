# RequireJS maven plugin

Builds javascript applications using the CommonJS Asynchronous Module Definition (AMD) 
pattern to define classes and dependencies between them. See:

http://wiki.commonjs.org/wiki/Modules/AsynchronousDefinition

## Origin

This project is originally based on Jacob Hansson's brew plugin at:

https://github.com/jakewins/brew

## Features

**simple**

The plugin has a very simple design. Just provide a json confg file for the
optimization process as documented at RequireJS.

**forward/backward/sideways compatible**

We'll make an effort to keep the r.js version embedded in the plugin up to date, but if
we've fallen behind, or you need to work with an older or custom version for some reason, it's as
simple as specifying the path to your version.

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
        </configuration>
      </plugin>
    </plugins>

### Goal: optimize

Uses the r.js optimizer to aggregate and minify your project. Dependencies should be defined using
the CommonJS Asynchronous Module Definition pattern, see the RequireJS documentation at:

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

The path to the optimizer js that will be run to optimize your app. If not provided, the default version
from the classpath will be used.

**filterConfig**

Boolean option to indicate whether or not to run the config file through maven filters to replace tokens
like ${basedir}

*Important Note:* if you enable filterConfig, be sure that the 'appDir' and 'dir' options in your config
use absolute paths. The filtering process creates a filtered copy of the config file in a temporary location,
which will break relative path handling for the input and output directories.
