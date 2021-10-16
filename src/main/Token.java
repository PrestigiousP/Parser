package main;

import main.tokenType;

public class Token {
    private final tokenType type;
    private String id;
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
                    type = tokenType.ID;
                    id = _type;
                }
            }
        }
    }

        /*

        Procedure nomMethode declare <variable> : <type> ;

        <instructions_affectation>
        Fin_Procedure <identificateur>2

    <déclaration> <déclarations>
    declare <variable> : <type> ;
    declare <variable> : <type> ;
         */

//     Procedure
//     nomMethode
//     declare id1 : entier;
//     declare id2 : entier;
//      id1 = 1;
//      id2 = 4 + id1;
//     Fin_Procedure

    public void addChar(char ch){
        value += ch;
    }

    public tokenType getTokenType(){
        return this.type;
    }

    public String getTokenValue(){
        return this.value;
    }
}
