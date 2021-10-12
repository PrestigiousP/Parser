public class Token {
    private tokenType type;
    private String value;
    public Token(String _type, String _value){
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
            case "(" -> type = tokenType.LEFTPARENT;
            case ")" -> type = tokenType.RIGHTPARENT;
            case "{" -> type = tokenType.LEFTCURL;
            case "}" -> type = tokenType.RIGHTCURL;
        }
        value = _value;
    }

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
