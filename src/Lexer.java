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
    private File sourceCode;
    public Lexer(File textFile, ArrayList<Token> tokenList){
        sourceCode = textFile;
        this.tokenList = tokenList;
        try {
            File myObj = new File("sourceCode.txt");
            Scanner reader = new Scanner(myObj);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                for(int i=0;i<data.length();i++){
                    System.out.println(data.charAt(i));
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
