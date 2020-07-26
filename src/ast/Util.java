package ast;

import ast.type.CompiType;
import ast.type.PrimitiveType;

/**
 *
 * @author Ronald
 */
public class Util {

  public static boolean resultIsInteger(CompiType t1, CompiType t2) {
    return isInteger(t1) && isInteger(t2);
  }

  public static boolean resultIsString(CompiType t1, CompiType t2) {
    return isString(t1) || isString(t2);
  }

  public static boolean resultIsDouble(CompiType t1, CompiType t2) {
    return (isDouble(t1) && isNumeric(t1)) || (isInteger(t1) && isDouble(t2));
  }

  public static boolean isNumeric(CompiType type) {
    return isInteger(type) || isDouble(type);
  }

  public static boolean isInteger(CompiType type) {
    return type == PrimitiveType.INTEGER;
  }

  public static boolean isDouble(CompiType type) {
    return type == PrimitiveType.DOUBLE;
  }

  public static boolean isString(CompiType type) {
    return type == PrimitiveType.STRING;
  }

  public static boolean isBoolean(CompiType type) {
    return type == PrimitiveType.BOOLEAN;
  }

  public static CompiType getStringType() {
    return PrimitiveType.STRING;
  }

  public static CompiType getIntegerType() {
    return PrimitiveType.INTEGER;
  }

  public static CompiType getDoubleType() {
    return PrimitiveType.DOUBLE;
  }

  public static CompiType getBooleanType() {
    return PrimitiveType.BOOLEAN;
  }

  public static int toInt(Object value) {
    return value instanceof Number ? ((Number) value).intValue() : 0;
  }

  public static double toDouble(Object value) {
    return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
  }
}
