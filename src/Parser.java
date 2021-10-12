import java.io.File;
import java.util.ArrayList;

public class Parser {
    private File sourceCode;
    private Lexer lexer;
    private ArrayList<Token> tokenList;
    public Parser(File textFile){
        sourceCode = textFile;
        tokenList = new ArrayList<>();
        lexer = new Lexer(textFile, tokenList);
        tokenList = lexer.getTokenList();
    }
}
