package ast.statement;

import ast.Ast;
import ast.AstNode;
import ast.expression.Expression;
import ast.scope.Scope;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public class Print extends AstNode {

  private final Expression expression;
  private final boolean lineBreak;

  public Print(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right,
          Expression expression,
          boolean lineBreak
  ) {
    super(left, right);
    this.expression = expression;
    this.lineBreak = lineBreak;
  }

  @Override
  public Object interpret(Scope scope) {
    Object value = expression.interpret(scope);
    if (lineBreak) {
      System.out.println(value);
    } else {
      System.out.print(value);
    }
    return null;
  }

  @Override
  public void graph(StringBuilder builder) {
  }

}
