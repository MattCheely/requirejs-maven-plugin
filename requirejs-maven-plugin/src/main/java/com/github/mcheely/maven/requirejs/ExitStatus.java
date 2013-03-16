package com.github.mcheely.maven.requirejs;

public class ExitStatus {
  private int exitCode;

public int getExitCode() {
  return exitCode;
}

public void setExitCode(int exitCode) {
  this.exitCode = exitCode;
}

public boolean success() {
  return (this.exitCode == 0);
}
}
