package ast.type;

/**
 *
 * @author Ronald
 */
public interface CompiType {
  boolean isEqual(CompiType otherType);
  String getName();
}
