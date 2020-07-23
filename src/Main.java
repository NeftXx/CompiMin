
import ast.Ast;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java_cup.runtime.ComplexSymbolFactory;
import lexer.Lexer;
import parser.Parser;

/**
 *
 * @author Ronald
 */
public class Main {

  /**
   * @param args the command line arguments
   * @throws java.lang.Exception
   */
  @SuppressWarnings("CallToPrintStackTrace")
  public static void main(String[] args) throws Exception {
    File file;
    if (args.length == 1) {
      file = new File(args[0]);
      if (!file.canRead()) {
        System.err.println("Error: could not read [" + file + "]");
      }
    } else {
      System.out.println("Warning: no arguments\n");
      file = new File(Main.class.getResource("hello.txt").getFile());
    }
    FileInputStream stream = new FileInputStream(file);
    Reader reader = new InputStreamReader(stream);
    ComplexSymbolFactory sf = new ComplexSymbolFactory();
    Lexer lexer = new Lexer(reader, sf, file.getName());
    Parser parse = new Parser(lexer, sf, file.getName());
    Ast ast;
    try {
      ast = (Ast) parse.parse().value;
      ast.interpret();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
