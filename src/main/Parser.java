package main;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Parser {
    private File sourceCode;
    private Lexer lexer;
    private ArrayList<Token> tokenList;
    private String identificateurMethode;
    private SpecialCharacter specialCharacter;
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
        procedure();
        // checkIds(tokenList);
    }

    private void procedure(){
        if(!searchForString("Procedure", index)){
            throw new Exception("mot clé \"Procédure\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index,1);
        identificateur(true);
        index = incremente(index,1);
        declarations();
        instructionsAffectation();
        index = incremente(index,1);
        if(!searchForString("Fin_Procedure", index)){
            throw new Exception("mot clé \"Fin_Procedure\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index,1);
        identificateur(false);
    }

    private void identificateur(boolean end){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8){
            if(end)
                identificateurMethode = tokenValue;
            else{
                if(!identificateurMethode.equals(tokenList.get(index).getTokenValue())){
                    throw new Exception("Erreur: l'identificateur attendu est \""+identificateurMethode+"\", mais a reçu \""+tokenList.get(index).getTokenValue()+"\", ligne "+tokenList.get(index).getLine());
                }
            }
        }
        else{
            if(!Character.isLetter(tokenValue.charAt(0))){
                throw new Exception("Erreur identificateur, ne commence pas par une lettre: "+tokenValue+",ligne "+tokenList.get(index).getLine());
            } else{
                throw new Exception("Erreur identificateur, est plus grand que 8 characteres: "+tokenValue.length()+",ligne "+tokenList.get(index).getLine());
            }
        }
    }

    private void declarations(){
        declaration();
        if(tokenList.get(index).getTokenValue().equals("declare")){
            declarations();
        }
    }

    private void declaration(){
        Token tk;
        if(!searchForString("declare", index)){
            throw new Exception("mot clé \"declare\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index,1);
        variable();
        tk = tokenList.get(index);
        index = incremente(index,1);
        if(!searchForString(":", index)){
            throw new Exception("mot clé \":\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index,1);
        type(tk);
        index = incremente(index,1);
        if(!searchForString(";", index)){
            throw new Exception("mot clé \";\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index,1);
    }

    /*
        Variable vérifie que les tokens de type variables respectent les critères.
        Le paramètre bool est vrai quand la variable est utilisée dans une déclaration et
        fausse quand elle est utilisée dans une expression.
     */
    private void variable(){
        String tokenValue = tokenList.get(index).getTokenValue();
        if(!(Character.isLetter(tokenValue.charAt(0)) && tokenValue.length() <= 8)){
            if(!Character.isLetter(tokenValue.charAt(0))){
                throw new Exception("Erreur variable, ne commence pas par une lettre: "+ tokenValue+", ligne "+tokenList.get(index).getLine());
            } else{
                throw new Exception("Erreur variable, est plus grand que 8 characteres: "+tokenValue.length()+",ligne "+tokenList.get(index).getLine());
            }
        }
        if(specialCharacter.isReservedKeyword(tokenValue)){
            throw new Exception("Erreur: utilisation illégal du mot clé \""+tokenValue+"\", ligne "+tokenList.get(index).getLine());
        }
    }

    private void type(Token tk){
        String str = "entier";
        String str2 = "reel";
        String tokenValue = tokenList.get(index).getTokenValue();
        if(str.equals(tokenValue)){
            tk.setVarType(tokenType.ENTIER);
            varList.add(tk);
        }
        else if (str2.equals(tokenValue)){
            tk.setVarType(tokenType.REEL);
            varList.add(tk);
        }
        else{
            throw new Exception("Erreur : Type invalide, ligne "+tokenList.get(index).getLine());
        }
    }

    private void instructionsAffectation(){
        instructionAffectation();
    }

    private void instructionAffectation(){
        Token tk;
        if(!isVariableDeclared(index)){
            throw new Exception("Erreur : variable non déclarée, ligne "+tokenList.get(index).getLine());
        }
        // Vérifie si les contraintes de variables sont respectées
        variable();
        // Va chercher le token de la liste
        tk = tokenList.get(index);
        // Va comparer les tokens et retourne le token de la varlist de tokens déclarés
        tk = getVarListToken(tk);
        index = incremente(index, 1);
        if(!searchForString("=", index)){
            throw new Exception("Erreur : opérateur \"=\" manquant, ligne "+tokenList.get(index).getLine());
        }
        index = incremente(index, 1);
        expressionArithmetique(tk);
        if(index+1 < tokenList.size()){
            if(!tokenList.get(index+1).getTokenValue().equals("Fin_Procedure")){
                index = incremente(index, 1);
                instructionAffectation();
            }
        }
    }

    private Token getVarListToken(Token tk) {
        for(Token token : varList){
            if(token.getTokenValue().equals(tk.getTokenValue())){
                return token;
            }
        }
        // todo: tester ici
        throw new Exception("Erreur: variable \""+tk+"\" n'est pas déclarée, ligne "+tokenList.get(index).getLine());
    }


    private void expressionArithmetique(Token tk){
        terme(tk);
        if(index+2 < tokenList.size()){
            if(searchForString("+", index+1) || searchForString("-", index+1)){
                index = incremente(index, 2);
                expressionArithmetique(tk);
            }
            else if(searchForString(";", index+1)){
                // expressionArithmetique(tk);
                index = incremente(index, 1);
            }
        }
        else{
            // todo: tester ici
            throw new Exception("Erreur: fonction incomplète, ligne "+tokenList.get(index).getLine());
        }
    }

    private void terme(Token tk){
        facteur(tk);
        if(index+2 < tokenList.size()){
            if(searchForString("*", index+1) || searchForString("/", index+1)){
                index = incremente(index, 2);
                facteur(tk);
            }
        }
        else{
            // todo: tester ici
            throw new Exception("Erreur: fonction incomplète, ligne "+tokenList.get(index).getLine());
        }
    }

    private void facteur(Token tk){
        if(tokenList.get(index).getTokenType() == tokenType.ID){
            if(!isVariableDeclared(index)){
                throw new Exception("Erreur : variable non déclarée, ligne "+tokenList.get(index).getLine());
            }
            // variable();
        }
        else if(tokenList.get(index).getTokenType() == tokenType.ENTIER) {
            if(tk.getVarType() != tokenType.ENTIER){
                // todo: tester ici
                throw new Exception("Erreur : Type invalide, attendu : "+tk.getVarType().toString()+", ligne "+tokenList.get(index).getLine());
            }
        } else if(tokenList.get(index).getTokenType() == tokenType.REEL){
            if(tk.getVarType() != tokenType.REEL){
                // todo: tester ici
                throw new Exception("Erreur : Type invalide, attendu : "+tk.getVarType().toString()+", ligne "+tokenList.get(index).getLine());

            }
        }
        else{
            throw new Exception("Erreur : expression arithmétique invalide, ligne "+tokenList.get(index).getLine());
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

    private int incremente(int index, int val){
        if(index+val < tokenList.size())
            index += val;
        else{
            throw new Exception("Erreur: jeton manquant, ligne "+tokenList.get(index).getLine());
        }
        return index;
    }

    /*
    Parcour la liste des variables déclarées et regarde si la variable est présente dans la liste.
    Si elle n'est pas présente, alors il y a nécessairement une erreur de syntaxe.
     */
    private boolean isVariableDeclared(int index){
        for(Token tk : varList){
            if(tk.getTokenValue().equals(tokenList.get(index).getTokenValue())){
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
