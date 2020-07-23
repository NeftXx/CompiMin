package ast;

import ast.scope.Scope;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public abstract class AstNode {

  private final ComplexSymbolFactory.Location left;
  private final ComplexSymbolFactory.Location right;

  protected AstNode(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right
  ) {
    this.left = left;
    this.right = right;
  }

  /**
   * @return the left
   */
  public ComplexSymbolFactory.Location getLeft() {
    return left;
  }

  /**
   * @return the right
   */
  public ComplexSymbolFactory.Location getRight() {
    return right;
  }

  public abstract Object interpret(Scope scope);

  public abstract void graph(StringBuilder builder);

}
