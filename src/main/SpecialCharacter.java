package main;

import java.util.HashMap;

public class SpecialCharacter {
    private final HashMap<String, Enum<tokenType>> specialCharacterTable;

    public SpecialCharacter(){
        specialCharacterTable = new HashMap<>();
        specialCharacterTable.put("+", tokenType.PLUS);
        specialCharacterTable.put("-", tokenType.MINUS);
        specialCharacterTable.put("*", tokenType.MULT);
        specialCharacterTable.put("/", tokenType.DIV);
        specialCharacterTable.put("=", tokenType.ASSIGN);
        specialCharacterTable.put(":", tokenType.COLON);
        specialCharacterTable.put(";", tokenType.SEMICOLON);
    }

    public boolean isSpecialCharacter(String str){
        return specialCharacterTable.containsKey(str);
    }
}
