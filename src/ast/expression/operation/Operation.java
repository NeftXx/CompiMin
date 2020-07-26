package ast.expression.operation;

import ast.expression.Expression;
import ast.type.CompiType;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public abstract class Operation extends Expression {

  protected final Expression expLeft, expRight;

  public Operation(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right,
          Expression expLeft, Expression expRight
  ) {
    super(left, right);
    this.expLeft = expLeft;
    this.expRight = expRight;
  }

  public static Exception createException(
          CompiType typeExpLeft, CompiType typeExpRight, String operator
  ) {
    return new Exception(
            "Error no se puede operar una expresión de tipo " +
            typeExpLeft + " con una expresión " + typeExpRight +
            " con el operador " + operator + "."
    );
  }
}
