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
        <version>1.1.0</version>
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
            <!--
            Whether or not to process configFile with maven filters.
            If you use this option, some options in your configFile
            must resolve to absolute paths (see below)
            -->
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

The path to the config file that will be passed to the r.js optimizer. This is equivalent to the -o argument when runing r.js from the command line.

**optimizerFile**

The path to the optimizer script (r.js) that will be run to optimize your app. If not provided, a default version packaged with the plugin. (currently v2.1.4)

**filterConfig**

Boolean option to indicate whether or not to run the config file through maven filters to replace tokens
like ${basedir} (defaults to false)

The filtered file is generated at ${project.build.directory}/requirejs-config/filtered-build.js.

*Important Note:* The RequireJS optimizer searches for js files relative to the config file's path. Because filtering
moves the effective config file to a new location, it is important that any 'baseUrl', 'appDir', or 'dir' options in
your config resolve absolute paths. The easiest way to do that is to use the maven path variables like ${basedir} to
prefix those potions.

**skip**

If skip is set to true, optimization will be skipped. This may be useful for reducing build time if optimization is not needed.
It can also be set via the command line with ```-Drequirejs.optimize.skip=true```.

**runner**

Specifies which Javascript engine is used to execute the r.js optimizer. Can be either *rhino* or *nodejs*, defaults to *rhino*.
When using *nodejs*, a NodeJS executable must be available on the build environment. By default the plugin assumes
an executable binary called 'node' is available on PATH. To customize the node executable's location, supply a path
to the executable using nodeJsFile

**nodeJsFile**

Path to NodeJS executable. Only applies when *runner* is set to *nodejs*. Default value: *node*

## Thanks

requirejs-maven-plugin is available on github because my previous employer, lulu.com, was great about letting me
share the work I do for them.

This project is originally based on Jacob Hansson's brew plugin at:
https://github.com/jakewins/brew
