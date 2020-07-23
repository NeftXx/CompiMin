/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ast.scope;

/**
 *
 * @author Ronald
 */
public class FileScope extends Scope {
  
  private final String filename;
  
  public FileScope(String filename) {
    super(null, null);
    this.filename = filename;
  }
  
  public String getFilename() {
    return this.filename;
  }

  @Override
  public FileScope getGlobal() {
    return this;
  }

  @Override
  public CompiVariable getVariableGlobal(String name) {
    return getVariableLocal(name);
  }
}
