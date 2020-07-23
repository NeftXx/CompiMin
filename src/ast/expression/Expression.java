package ast.expression;

import ast.AstNode;
import ast.type.CompiType;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public abstract class Expression extends AstNode {

  protected CompiType type;
  protected Object value;

  public Expression(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right
  ) {
    super(left, right);
  }

  public CompiType getType() {
    return this.type;
  }

  public Object getValue() {
    return this.value;
  }
}
