package main;

import java.util.HashMap;

public class SpecialCharacter {
    private final HashMap<String, Enum<tokenType>> specialCharacterTable;
    private final HashMap<String, Enum<tokenType>> reservedKeywords;

    public SpecialCharacter(){
        specialCharacterTable = new HashMap<>();
        specialCharacterTable.put("+", tokenType.PLUS);
        specialCharacterTable.put("-", tokenType.MINUS);
        specialCharacterTable.put("*", tokenType.MULT);
        specialCharacterTable.put("/", tokenType.DIV);
        specialCharacterTable.put("=", tokenType.ASSIGN);
        specialCharacterTable.put(":", tokenType.COLON);
        specialCharacterTable.put(";", tokenType.SEMICOLON);
        reservedKeywords = new HashMap<>();
        reservedKeywords.put("Procedure", tokenType.PROCEDURE);
        reservedKeywords.put("Fin_Procedure", tokenType.FIN_PROCEDURE);
        reservedKeywords.put("declare", tokenType.DECLARE);
        reservedKeywords.put("entier", tokenType.ENTIER);
        reservedKeywords.put("reel", tokenType.REEL);
    }

    public boolean isSpecialCharacter(String str){return specialCharacterTable.containsKey(str);}
    public boolean isReservedKeyword(String str){return reservedKeywords.containsKey(str);}
}
