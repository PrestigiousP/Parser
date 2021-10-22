package main;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {
    private File sourceCode;
    private Lexer lexer;
    private ArrayList<Token> tokenList;
    private SpecialCharacter specialCharacter;
    private String identificateurMethode;
    private ArrayList<Token> varList;
    private int index;

    public Parser(File textFile){
        index = 0;
        varList = new ArrayList<>();
        sourceCode = textFile;
        tokenList = new ArrayList<>();
        specialCharacter = new SpecialCharacter();
        lexer = new Lexer(textFile, tokenList, specialCharacter);
        tokenList = lexer.tokenize();
        // procedure(tokenList);
        // checkIds(tokenList);

        // checkIds(tokenList);
    }

    private void procedure(ArrayList<Token> tokenList){
        searchForString(tokenList, "Procedure", index);
        index = incremente(index);
        identificateur(tokenList);
        index = incremente(index);
        declarations(tokenList);
        instructionsAffectation(tokenList);
    }

    private void identificateur(ArrayList<Token> tokenList){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8){
            identificateurMethode = tokenValue;
        }
        else{
            System.out.println("identificateur invalide");
        }
    }

    private void declarations(ArrayList<Token> tokenList){
        declaration(tokenList);
//        System.out.println(tokenList.get(index).getTokenId());
        if(tokenList.get(index).getTokenValue().equals("declare")){
            declarations(tokenList);
        }
//        System.out.println(tokenList.get(index).getTokenId());
    }

    private void declaration(ArrayList<Token> tokenList){
//        System.out.println(tokenList.get(index).getTokenId());
        Token tk;
        searchForString(tokenList,"declare", index);
        index = incremente(index);
        tk = variable(tokenList);
        index = incremente(index);
        searchForString(tokenList, ":", index);
        index = incremente(index);
        type(tokenList, tk);
        index = incremente(index);
        searchForString(tokenList, ";", index);
        index = incremente(index);
        instructionsAffectation(tokenList);
    }

    private Token variable(ArrayList<Token> tokenList){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(!(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8)){
            System.out.println("erreur variable");
        }
        else{
            varList.add(tokenList.get(index));
            //return tokenList.get(index);
        }
        return tokenList.get(index);
    }

    private void type(ArrayList<Token> tokenList, Token tk){
        String str = "entier";
        String str2 = "reel";
        String tokenValue = tokenList.get(index).getTokenValue();
        if(str.equals(tokenValue)){
            tk.setVarType(tokenType.ENTIER);
        }
        else if (str2.equals(tokenValue)){
            tk.setVarType(tokenType.REEL);
        }
        else{
            System.out.println("erreur type invalide");
        }
    }

    private void instructionsAffectation(ArrayList<Token> tokenList){
        instructionAffectation(tokenList);
    }

    private void instructionAffectation(ArrayList<Token> tokenList){
        variable(tokenList);
        index = incremente(index);
        searchForString(tokenList, "=", index);
        index = incremente(index);
        expressionArithmetique(tokenList);
    }


    private void expressionArithmetique(ArrayList<Token> tokenList){
        terme(tokenList);
    }

    private void terme(ArrayList<Token> tokenList){
        facteur(tokenList);
    }

    private void facteur(ArrayList<Token> tokenList){
        if(tokenList.get(index).getTokenType() == tokenType.ID){
            variable(tokenList);
        }
        else if(tokenList.get(index).getTokenType() == tokenType.ENTIER) {

        } else if(tokenList.get(index).getTokenType() == tokenType.REEL){

        }
        else{
            expressionArithmetique(tokenList);
        }
    }

    private void searchForString(ArrayList<Token> tokenList, String str, int index){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(str.equals(tokenValue)){

        }
        else{
            System.out.println("erreur");
        }
    }

    private int incremente(int index){
        index++;
        return index;
    }

    public void checkIds(ArrayList<Token> tokenList){
        tokenList.forEach(token -> {
            if(token.getTokenType() == tokenType.ID){
                System.out.println(token.getTokenValue());
            }
        });
    }
}
