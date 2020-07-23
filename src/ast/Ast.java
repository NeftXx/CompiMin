package ast;

import ast.scope.FileScope;
import java.util.ArrayList;

/**
 *
 * @author Ronald
 */
public class Ast {

  private final ArrayList<AstNode> nodes;
  private final String filename;

  public Ast(String filename, ArrayList<AstNode> nodes) {
    this.nodes = nodes;
    this.filename = filename;
  }

  public void interpret() {
    FileScope global = new FileScope(filename);
    nodes.forEach((node) -> {
      node.interpret(global);
    });
  }
}
