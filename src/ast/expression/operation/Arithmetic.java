package ast.expression.operation;

import ast.Util;
import ast.expression.Expression;
import ast.scope.Scope;
import ast.type.CompiType;
import java_cup.runtime.ComplexSymbolFactory;

/**
 *
 * @author Ronald
 */
public class Arithmetic extends Operation {

  public enum Operator {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    MODULE("%");

    private final String sign;

    Operator(String sign) {
      this.sign = sign;
    }

    @Override
    public String toString() {
      return this.sign;
    }
  }

  private final Operator operator;

  public Arithmetic(
          ComplexSymbolFactory.Location left,
          ComplexSymbolFactory.Location right,
          Expression expLeft, Expression expRight,
          Operator operator
  ) {
    super(left, right, expLeft, expRight);
    this.operator = operator;
  }

  @Override
  public Object interpret(Scope scope) throws Exception  {
    Object valueExp1 = this.expLeft.interpret(scope);
    Object valueExp2 = this.expRight.interpret(scope);
    switch (operator) {
      case ADDITION:
        return addition(valueExp1, valueExp2);
      default:
        return operations(valueExp1, valueExp2);
    }
  }
  
  public Object addition(Object valueExp1, Object valueExp2) throws Exception {
    final CompiType typeExpLeft = this.expLeft.getType();
    final CompiType typeExpRight = this.expRight.getType();
    if (Util.resultIsString(typeExpLeft, typeExpRight)) {
      String val1 = valueExp1 == null ? "null" : valueExp1.toString();
      String val2 = valueExp2 == null ? "null" : valueExp2.toString();
      this.type = Util.getStringType();
      return val1 + val2;
    }

    if (Util.resultIsInteger(typeExpLeft, typeExpRight)) {
      this.type = Util.getIntegerType();
      return Util.toInt(valueExp1) + Util.toInt(valueExp2);
    }
    
    if (Util.resultIsDouble(typeExpLeft, typeExpRight)) {
      this.type = Util.getDoubleType();
      return Util.toDouble(valueExp1) + Util.toDouble(valueExp2);
    }

    throw Operation.createException(
      typeExpLeft, typeExpRight, operator.toString()
    );
  }
  
  public Object operations(Object valueExp1, Object valueExp2) throws Exception {
    final CompiType typeExpLeft = this.expLeft.getType();
    final CompiType typeExpRight = this.expRight.getType();
    
    if (Util.resultIsInteger(typeExpLeft, typeExpRight)) {
      this.type = Util.getIntegerType();
      int val1 = Util.toInt(valueExp1);
      int val2 = Util.toInt(valueExp2);
      switch(this.operator) {
        case SUBTRACTION:
          return val1 - val2;
        case MULTIPLICATION:
          return val1 * val2;
        case DIVISION:
          return val1 / val2;
        case MODULE:
          return val1 % val2;
        default:
          throw new UnsupportedOperationException(
                  "No existe operación válida con el operador" + operator
          );
      }      
    }

    if (Util.resultIsDouble(typeExpLeft, typeExpRight)) {
      this.type = Util.getDoubleType();
      double val1 = Util.toDouble(valueExp1);
      double val2 = Util.toDouble(valueExp2);
      switch(this.operator) {
        case SUBTRACTION:
          return val1 - val2;
        case MULTIPLICATION:
          return val1 * val2;
        case DIVISION:
          return val1 / val2;
        case MODULE:
          return val1 % val2;
        default:
          throw new UnsupportedOperationException(
                  "No existe operación válida con el operador" + operator
          );
      }
    }

    throw Operation.createException(
      typeExpLeft, typeExpRight, operator.toString()
    );
  }

  @Override
  public void graph(StringBuilder builder) {
  }

}
