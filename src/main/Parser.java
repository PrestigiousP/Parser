package main;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {
    private File sourceCode;
    private Lexer lexer;
    private ArrayList<Token> tokenList;
    private String identificateurMethode;
    private ArrayList<Token> varList;
    private int index;

    public Parser(File textFile){
        index = 0;
        varList = new ArrayList<>();
        sourceCode = textFile;
        tokenList = new ArrayList<>();
        SpecialCharacter specialCharacter = new SpecialCharacter();
        lexer = new Lexer(textFile, tokenList, specialCharacter);
        tokenList = lexer.tokenize();
        procedure();
        // checkIds(tokenList);
    }

    private void procedure(){
        if(!searchForString("Procedure", index)){
            System.out.println("mot clé \"Procédure\" manquant");
        }
        index++;
        identificateur();
        index++;
        declarations();
        instructionsAffectation();
    }

    private void identificateur(){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8){
            identificateurMethode = tokenValue;
        }
        else{
            System.out.println("identificateur invalide");
        }
    }

    private void declarations(){
        declaration();
//        System.out.println(tokenList.get(index).getTokenId());
        if(tokenList.get(index).getTokenValue().equals("declare")){
            declarations();
        }
//        System.out.println(tokenList.get(index).getTokenId());
    }

    private void declaration(){
//        System.out.println(tokenList.get(index).getTokenId());
        Token tk;
        if(!searchForString("declare", index)){
            System.out.println("mot clé \"declare\" manquant");
        }
        index++;
        variable(true);
        tk = tokenList.get(index);
        index++;
        if(!searchForString(":", index)){
            System.out.println("mot clé \":\" manquant");
        }
        index++;
        type(tk);
        index++;
        if(!searchForString(";", index)){
            System.out.println("mot clé \";\" manquant");
        }
        index++;
    }

    /*
        Variable vérifie que les tokens de type variables respectent les critères.
        Le paramètre bool est vrai quand la variable est utilisée dans une déclaration et
        fausse quand elle est utilisée dans une expression.
     */
    private void variable(boolean bool){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(!(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8)){
            if(!Character.isLetter(tokenValue.charAt(0))){
                throw new Exception("Erreur variable, ne commence pas par une lettre: ", tokenValue);
            } else{
                throw new Exception("Erreur variable, est plus grand que 8 characteres: ", tokenValue.length());
            }

        }
        else{
            if(bool){
                varList.add(tokenList.get(index));
            }
        }
    }

    private void type(Token tk){
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
            throw new Exception("Erreur : Type illégal n'est pas entier ou réel", tk.getVarType().toString());
        }
    }

    private void instructionsAffectation(){
        instructionAffectation();
    }

    private void instructionAffectation(){
        Token tk;
        if(!isVariableDeclared(index)){
            throw new Exception("Erreur : variable non déclarée");
        }
        variable(false);
        // TODO: vérifier que si y'a une erreur dans variable() que ça pose pas problème pour tk ici
        tk = varList.get(varList.size()-1);
        index++;
        if(!searchForString("=", index)){
            System.out.println("mot clé \"=\" manquant");
        }
        index++;
        expressionArithmetique(tk);
    }


    private void expressionArithmetique(Token tk){
        terme(tk);
        index++;
        if(!searchForString("+", index) || !searchForString("-", index)){
            terme(tk);
        }
        if(!searchForString(";", index)){
            expressionArithmetique(tk);
        }
    }

    private void terme(Token tk){
        facteur(tk);
        index++;
        if(!searchForString("*", index) || !searchForString("/", index)){
            facteur(tk);
        }
    }

    private void facteur(Token tk){
        if(tokenList.get(index).getTokenType() == tokenType.ID){
            if(!isVariableDeclared(index)){
                throw new Exception("Erreur : variable non déclarée");
            }
            variable(false);
        }
        else if(tokenList.get(index).getTokenType() == tokenType.ENTIER) {
            if(tk.getVarType() != tokenType.ENTIER){
                //todo: lancher une erreur
                throw new Exception("Erreur : Type illégal, n'est pas entier : ", tk.getVarType().toString());
            }
        } else if(tokenList.get(index).getTokenType() == tokenType.REEL){
            if(tk.getVarType() != tokenType.REEL){
                //todo: lancer une erreur
                throw new Exception("Erreur : Type illégal, n'est pas réel : ", tk.getVarType().toString());

            }
        }
        else{
            // System.out.println("appel exprArith");
            // expressionArithmetique(tk);
        }
    }

    private boolean searchForString(String str, int index){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(str.equals(tokenValue)){
            return true;
        }
        else{
            // System.out.println("erreur: " + str);
            return false;
        }
    }

    /*
    Parcour la liste des variables déclarées et regarde si la variable est présente dans la liste.
    Si elle n'est pas présente, alors il y a nécessairement une erreur de syntaxe.
     */
    private boolean isVariableDeclared(int index){
        for(Token tk : varList){
            if(tk.getTokenValue().equals(tokenList.get(index).getTokenValue())){
                // System.out.println("erreur, variable non déclarée");
                return true;
            }
        }
        return false;
    }

    public void checkIds(ArrayList<Token> tokenList){
        tokenList.forEach(token -> {
            if(token.getTokenType() == tokenType.ID){
                System.out.println(token.getTokenValue());
            }
        });
    }
}
