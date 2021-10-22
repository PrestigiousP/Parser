package main;

import main.tokenType;

public class Token {
    private final tokenType type;
    // type que la variable peut prendre (après déclaration)
    private tokenType varType;
    private String  value;

    public Token(String _type) {
        // nombre reel
        if(_type.matches("-?\\d+\\.\\d+")){
            type = tokenType.REEL;
        }
        // nombre entier
        else if(_type.matches("-?\\d+")){
            type = tokenType.ENTIER;
        }
        else{
            switch (_type) {
                case "+" -> type = tokenType.PLUS;
                case "-" -> type = tokenType.MINUS;
                case "*" -> type = tokenType.MULT;
                case "/" -> type = tokenType.DIV;
                case "Procedure" -> type = tokenType.PROCEDURE;
                case "Fin_Procedure" -> type = tokenType.FIN_PROCEDURE;
                case "=" -> type = tokenType.ASSIGN;
                case ":" -> type = tokenType.COLON;
                case ";" -> type = tokenType.SEMICOLON;
                case "declare" -> type = tokenType.DECLARE;
                case "entier" -> type = tokenType.ENTIER;
                case "reel" -> type = tokenType.REEL;
                default -> {
                    // System.out.println(_type);
                    type = tokenType.ID;
                }
            }
        }
        value = _type;
    }
    public void addChar(char ch){
        value += ch;
    }

    public void setVarType(tokenType tk) {
        this.varType = tk;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public tokenType getVarType(){
        return this.varType;
    }

    public tokenType getTokenType(){
        return this.type;
    }

    public String getTokenValue(){
        return this.value;
    }
}
