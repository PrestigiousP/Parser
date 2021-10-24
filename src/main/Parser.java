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
        procedure(tokenList);
        // checkIds(tokenList);
    }

    private void procedure(ArrayList<Token> tokenList){
        if(!searchForString(tokenList, "Procedure", index)){
            System.out.println("mot clé \"Procédure\" manquant");
        }
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
        if(!searchForString(tokenList,"declare", index)){
            System.out.println("mot clé \"declare\" manquant");
        }
        index = incremente(index);
        variable(tokenList, true);
        tk = tokenList.get(index);
        index = incremente(index);
        if(searchForString(tokenList, ":", index)){
            System.out.println("mot clé \":\" manquant");
        }
        index = incremente(index);
        type(tokenList, tk);
        index = incremente(index);
        if(searchForString(tokenList, ";", index)){
            System.out.println("mot clé \";\" manquant");
        }
        index = incremente(index);
    }

    /*
        Variable vérifie que les tokens de type variables respectent les critères.
        Le paramètre bool est vrai quand la variable est utilisée dans une déclaration et
        fausse quand elle est utilisée dans une expression.
     */
    private void variable(ArrayList<Token> tokenList, boolean bool){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(!(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8)){
            System.out.println("erreur variable");
        }
        else{
            if(bool){
                varList.add(tokenList.get(index));
            }
        }
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
        Token tk;
        isVariableDeclared(index);
        variable(tokenList, false);
        // TODO: vérifier que si y'a une erreur dans variable() que ça pose pas problème pour tk ici
        tk = varList.get(varList.size()-1);
        index = incremente(index);
        if(searchForString(tokenList, "=", index)){
            System.out.println("mot clé \"=\" manquant");
        }
        index = incremente(index);
        expressionArithmetique(tokenList, tk);
    }


    private void expressionArithmetique(ArrayList<Token> tokenList, Token tk){
        terme(tokenList, tk);
        index = incremente(index);
        if(searchForString(tokenList, "+", index) || searchForString(tokenList, "-", index)){
            terme(tokenList, tk);
        }
        if(!searchForString(tokenList, ";", index)){
            expressionArithmetique(tokenList, tk);
        }
    }

    private void terme(ArrayList<Token> tokenList, Token tk){
        facteur(tokenList, tk);
        index = incremente(index);
        if(searchForString(tokenList, "*", index) || searchForString(tokenList, "/", index)){
            facteur(tokenList, tk);
        }
    }

    private void facteur(ArrayList<Token> tokenList, Token tk){
        if(tokenList.get(index).getTokenType() == tokenType.ID){
            isVariableDeclared(index);
            variable(tokenList,false);
        }
        else if(tokenList.get(index).getTokenType() == tokenType.ENTIER) {
            if(tk.getVarType() != tokenType.ENTIER){
                //todo: lancher une erreur
                System.out.println("type illégal");
            }
        } else if(tokenList.get(index).getTokenType() == tokenType.REEL){
            if(tk.getVarType() != tokenType.REEL){
                //todo: lancer une erreur
                System.out.println("type illégal");
            }
        }
        else{
            System.out.println("appel exprArith");
            //expressionArithmetique(tokenList, tk);
        }
    }

    private boolean searchForString(ArrayList<Token> tokenList, String str, int index){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(str.equals(tokenValue)){
            return true;
        }
        else{
            System.out.println("erreur: " + str);
            return false;
        }
    }

    private int incremente(int index){
        index++;
        return index;
    }

    /*
    Parcour la liste des variables déclarées et regarde si la variable est présente dans la liste.
    Si elle n'est pas présente, alors il y a nécessairement une erreur de syntaxe.
     */
    private void isVariableDeclared(int index){
        for(Token tk : varList){
            if(!tk.getTokenValue().equals(varList.get(index).getTokenValue())){
                System.out.println("erreur, variable non déclarée");
            }
        }
    }

    public void checkIds(ArrayList<Token> tokenList){
        tokenList.forEach(token -> {
            if(token.getTokenType() == tokenType.ID){
                System.out.println(token.getTokenValue());
            }
        });
    }
}
