public class Token {
    private tokenType type;
    private String value;
    public Token(String _type, String _value){
        switch (_type) {
            case "+" -> type = tokenType.PLUS;
            case "-" -> type = tokenType.MINUS;
            case "*" -> type = tokenType.MULT;
            case "/" -> type = tokenType.DIV;
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
