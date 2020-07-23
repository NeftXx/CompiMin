package ast.type;

import java.util.HashMap;

/**
 *
 * @author Ronald
 */
public class ObjectType implements CompiType {

  private final String name;

  private ObjectType(String name) {
    this.name = name;
  }

  public static boolean createNewObjectType(String name) {
    if (types.containsKey(name)) {
      return false;
    }
    types.put(name, new ObjectType(name));
    return true;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean isEqual(CompiType otherType) {
    return this == otherType;
  }

  private static final HashMap<String, ObjectType> types = new HashMap<>();
}
