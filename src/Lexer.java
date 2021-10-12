import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {
    private int position;
    private char currentChar;
    private ArrayList<Token> tokenList;
    private int liCount;
    private int colCount;
    private String token;
    private File sourceCode;

    public Lexer(File textFile, ArrayList<Token> tokenList){
        sourceCode = textFile;
        this.tokenList = tokenList;
        createTokenList();
    }

    private void createTokenList() {
        try {
            Scanner reader = new Scanner(sourceCode);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                for(int i=0;i<data.length();i++){
                    char ch = data.charAt(i);
                    // Ici on peut accéder à tous les char
                    if(!Character.isWhitespace(ch)){

                        System.out.println(data.charAt(i));
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public ArrayList<Token> getTokenList(){
        return this.tokenList;
    }
}
