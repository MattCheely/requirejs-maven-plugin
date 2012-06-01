# RequreJS maven plugin

Builds javascript applications using the CommonJS Asynchronous Module Definition (AMD) 
pattern to define classes and dependencies between them. See:

http://wiki.commonjs.org/wiki/Modules/AsynchronousDefinition

### Origin

This project is originally based on Jacob Hansson's brew plugin at:

https://github.com/jakewins/brew

### Usage

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
        </configuration>
      </plugin>
    </plugins>

### Goal: optimize

Uses the r.js optimizer to aggregate and minify your project. Dependencies should be 
defined using the CommonJS Asynchronous Module Definition pattern, see the RequireJS documentation at:

http://requirejs.org

The "optimize" goal is by default attached to the "process-classes" maven phase. It will go through
all the js modules you have listed in your plugin configuration, interpret the AMD dependencies
each file has, aggregate and minify that entire dependency tree, and put the resulting minified filed in 
your output directory.

Optimization is configured using a json project configuration file. For details of the options available,
see the RequireJS optimization documentation at:

http://requirejs.org/docs/optimization.html#wholeproject

The plugin also takes an optional optimizerFile parameter, which can be used to specify the path to a
version of r.js or some compatible optimizer other than the one packaged with the plugin.
