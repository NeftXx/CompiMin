package ast.type;

/**
 *
 * @author Ronald
 */
public class PrimitiveType implements CompiType {

  private final String name;

  private PrimitiveType(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean isEqual(CompiType otherType) {
    return this == otherType;
  }

  public static final PrimitiveType INTEGER = new PrimitiveType("integer");
  public static final PrimitiveType DOUBLE = new PrimitiveType("double");
  public static final PrimitiveType BOOLEAN = new PrimitiveType("boolean");
  public static final PrimitiveType STRING = new PrimitiveType("String");
}
