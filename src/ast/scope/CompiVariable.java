package ast.scope;

import ast.type.CompiType;

/**
 *
 * @author Ronald
 */
public class CompiVariable {

  private final String name;
  private final CompiType type;
  private Object value;
  
  public CompiVariable(String name, CompiType type, Object value) {
    this.name = name;
    this.type = type;
    this.value = value;
  }

  public String getName() {
    return this.name;
  }

  public CompiType getType() {
    return this.type;
  }
  
  public Object getValue() {
    return this.value;
  }
  
  public void setValue(Object value) {
    this.value = value;
  }

  @Override
  public String toString() {
    if (this.value != null) {
      return this.value.toString();
    }
    return "null";
  }
}
