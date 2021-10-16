package main;

import java.io.File;
import java.util.ArrayList;

public class Parser {
    private File sourceCode;
    private Lexer lexer;
    private ArrayList<Token> tokenList;
    private SpecialCharacter specialCharacter;

    public Parser(File textFile){
        sourceCode = textFile;
        tokenList = new ArrayList<>();
        specialCharacter = new SpecialCharacter();
        lexer = new Lexer(textFile, tokenList, specialCharacter);
        tokenList = lexer.tokenize();
    }
}
