({
	/*
	 * The top level directory that contains your app. If this option is used
	 *  then it assumed your scripts are in a subdirectory under this path.
	 *  This option is not required. If it is not specified, then baseUrl
	 *  below is the anchor point for finding things. If this option is specified,
	 *  then all the files from the app directory will be copied to the dir:
	 *  output area, and baseUrl will assume to be a relative path under
	 *  this directory.
	 *  
	 *  Points to 'src/main/webapp/js' folder, which is the top level directory that contains all the javaScript
	 */
	appDir: './',
	/*
	 * By default, all modules are located relative to this path. If baseUrl
     * is not explicitly set, then all modules are loaded relative to
     * the directory that holds the build file. If appDir is set, then
     * baseUrl should be specified as relative to the appDir.
	 */
    baseUrl: "./js",
    /*
     * The directory path to save the output. If not specified, then
     * the path will default to be a directory called "build" as a sibling
     * to the build file. All relative paths are relative to the build file.
     */
    dir: "../output/2.1",
    /*
     *  How to optimize all the JS files in the build output directory.
     *  Right now only the following values
     *  are supported:
     *  - "uglify": (default) uses UglifyJS to minify the code.
     *  - "closure": uses Google's Closure Compiler in simple optimization
     *  mode to minify the code. Only available if running the optimizer using
     *  Java.
     *  - "closure.keepLines": Same as closure option, but keeps line returns
     *  in the minified files.
     *  - "none": no minification will be done.
     */
    optimize: "uglify",

    /*
     *  Set paths for modules. If relative paths, set relative to baseUrl above.
     *  If a special value of "empty:" is used for the path value, then that
     *  acts like mapping the path to an empty file. It allows the optimizer to
     *  resolve the dependency to path, but then does not include it in the output.
     *  Useful to map module names that are to resources on a CDN or other
     *  http: URL when running in the browser and during an optimization that
     *  file should be skipped because it has no dependencies.
     */
    paths: {
    	"jquery": "lib/jquery-1.7.2",
		"json": "lib/json2",
		"underscore": "lib/underscore-1.3.3",
		"backbone": "lib/backbone-0.9.2",
		"dust": "lib/dust-core-1.1.0",
		"bootstrap": "lib/bootstrap",
		"base": "core/base"
    },
    
    /*
     * If set to true, any files that were combined into a build layer will be
     * removed from the output folder.
     */
    removeCombined: true,
    
    /*
     * When the optimizer copies files from the source location to the
     * destination directory, it will skip directories and files that start
     * with a ".". If you want to copy .directories or certain .files, for
     * instance if you keep some packages in a .packages directory, or copy
     * over .htaccess files, you can set this to null. If you want to change
     * the exclusion rules, change it to a different regexp. If the regexp
     * matches, it means the directory will be excluded.
     * 
     */
    fileExclusionRegExp: "/^\./",

    modules: [
        {
        	name: "main"
        }
    ]
})