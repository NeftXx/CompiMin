package ast.expression;

import ast.scope.Scope;
import ast.type.CompiType;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public class Literal extends Expression {

  public Literal(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right,
          CompiType type, Object value
  ) {
    super(left, right);
    this.type = type;
    this.value = value;
  }

  @Override
  public Object interpret(Scope scope) {
    return this.value;
  }

  @Override
  public void graph(StringBuilder builder) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

}
