/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is Rhino code, released
 * May 6, 1998.
 *
 * The Initial Developer of the Original Code is
 * Netscape Communications Corporation.
 * Portions created by the Initial Developer are Copyright (C) 1997-1999
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 *
 * Alternatively, the contents of this file may be used under the terms of
 * the GNU General Public License Version 2 or later (the "GPL"), in which
 * case the provisions of the GPL are applicable instead of those above. If
 * you wish to allow use of your version of this file only under the terms of
 * the GPL and not to allow others to use your version of this file under the
 * MPL, indicate your decision by deleting the provisions above and replacing
 * them with the notice and other provisions required by the GPL. If you do
 * not delete the provisions above, a recipient may use your version of this
 * file under either the MPL or the GPL.
 *
 * ***** END LICENSE BLOCK ***** */

package com.github.mcheely.maven.requirejs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextAction;
import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.Kit;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.tools.SourceReader;
import org.mozilla.javascript.tools.shell.Global;
import org.mozilla.javascript.tools.shell.QuitAction;
import org.mozilla.javascript.tools.shell.ShellContextFactory;

/**
 * Class for running a single js file. This is just a stripped down
 * version of org.mozilla.javascript.tools.shell.Main
 * 
 * @author Norris Boyd
 * @author Matthew Cheely
 */
public class RhinoRunner  {

    private static final long serialVersionUID = 3859222870741981547L;
    
    /**
     * Proxy class to avoid proliferation of anonymous classes.
     */
    private static class IProxy implements ContextAction, QuitAction
    {
        private static final int PROCESS_FILES = 1;
        private static final int SYSTEM_EXIT = 3;

        private int type;
        String[] args;

        IProxy(int type)
        {
            this.type = type;
        }

        public Object run(Context cx)
        {
            if (type == PROCESS_FILES) {
                processFiles(cx, args);
            } else {
                throw Kit.codeBug();
            }
            return null;
        }

        public void quit(Context cx, int exitCode)
        {
            if (type == SYSTEM_EXIT) {
                System.exit(exitCode);
                return;
            }
            throw Kit.codeBug();
        }
    }
    
    private static ShellContextFactory shellContextFactory = new ShellContextFactory();
    private static Global global = new Global();
    private static List<File> fileList = new ArrayList<File>();

    /**
     * Execute a js file.
     * @param includes other js files to include and parse before executing the main script.
     * @param mainScript the script to run.
     * @param args arguments that will be visible to the script.
     * @param globalVariables global js variables to set.
     * @param reporter error reporter.
     */
    public static void exec(File mainScript, String[] args, ErrorReporter reporter) {
        shellContextFactory.setErrorReporter(reporter);
        
        global.init(shellContextFactory);
        
        fileList.add(mainScript);
        
        IProxy iproxy = new IProxy(IProxy.PROCESS_FILES);
        iproxy.args = args;
        shellContextFactory.call(iproxy);
    }
    
    static void processFiles(Context cx, String[] args) {
        // define "arguments" array in the top-level object:
        // need to allocate new array since newArray requires instances
        // of exactly Object[], not ObjectSubclass[]
        Object[] array = new Object[args.length];
        System.arraycopy(args, 0, array, 0, args.length);
        Scriptable argsObj = cx.newArray(global, array);
        global.defineProperty("arguments", argsObj,
                              ScriptableObject.DONTENUM);

        for (File file: fileList) {
            try {
                processFile(cx, global, file);
            } catch (IOException ioex) {
                Context.reportError("Could not read file: " + file.getAbsolutePath());
            } catch (RhinoException rex) {
                Context.reportError("Rhino Exception: " + rex.getMessage());
            } catch (VirtualMachineError ex) {
                Context.reportError("Uncaught javascript exception: " + ex.toString());
            }
        }
    }
    
    static void processFile(Context cx, Scriptable scope, File file) throws IOException {
        String path = file.getAbsolutePath();
        Object source = readFileOrUrl(path, true);
        Script script;
        
        String strSrc = (String) source;
        // Support the executable script #! syntax: If
        // the first line begins with a '#', treat the whole
        // line as a comment.
        if (strSrc.length() > 0 && strSrc.charAt(0) == '#') {
            for (int i = 1; i != strSrc.length(); ++i) {
              int c = strSrc.charAt(i);
              if (c == '\n' || c == '\r') {
                  strSrc = strSrc.substring(i);
                  break;
              }
            }
        }
        
        script = cx.compileString(strSrc, path, 1, null);
        
        if (script != null) {
            script.exec(cx, scope);
        }
    }

    /**
     * Read file or url specified by <tt>path</tt>.
     * @return file or url content as <tt>byte[]</tt> or as <tt>String</tt> if
     * <tt>convertToString</tt> is true.
     */
    private static Object readFileOrUrl(String path, boolean convertToString) throws IOException {
        return SourceReader.readFileOrUrl(path, convertToString, shellContextFactory.getCharacterEncoding());
    }
  
}
