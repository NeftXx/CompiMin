package ast.scope;

import java.util.HashMap;

/**
 *
 * @author Ronald
 */
public class Scope {

  private final HashMap<String, CompiVariable> variables;
  private final Scope previous;
  private final FileScope global;

  public Scope(Scope previous, FileScope global) {
    this.previous = previous;
    this.global = global;
    this.variables = new HashMap<>();
  }

  public FileScope getGlobal() {
    return this.global;
  }

  public CompiVariable getVariable(String name) {
    for (Scope scope = this; scope != null; scope = scope.previous) {
      CompiVariable found = scope.variables.get(name);
      if (found != null) {
        return found;
      }
    }
    return null;
  }

  public CompiVariable getVariableLocal(String name) {
    return variables.get(name);
  }

  public CompiVariable getVariableGlobal(String name) {
    if (this.global != null) {
      return this.global.getVariableLocal(name);
    }
    return null;
  }
}
