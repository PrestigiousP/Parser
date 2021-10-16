import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {
    private int position;
    private char currentChar;
    private ArrayList<Token> tokenList;
    private String tokenPart ="";
    private int liCount;
    private int colCount;
    private String token;
    private File sourceCode;
    private boolean tokenIsSaved;// permet de vérifier si'il y a eu un enregistrement pour éviter le double enregistrement
    private String specialChars = "(){}+-=*";

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
                System.out.println("reading line: " + data);
                for(int i=0;i<data.length();i++){
                    tokenIsSaved = false;
                    char ch = data.charAt(i);
//                    System.out.println("present char: "+ch);
                    tokenPart = "";

                    if(Character.isDigit(ch)) {
                        while(Character.isDigit(ch)){
                            tokenPart += ch;
                            i++;
                            if(i == data.length()) break;
                            ch = data.charAt(i);
                        }
                        if(Character.isDigit(ch))
                        { // on veut s'assurer que le dernier charactere n'est pas une lettre ou autre
                            System.out.println("enregistre chiffre");
                            tokenList.add(new Token("entier",tokenPart));
                            System.out.println(tokenPart);
                            System.out.println(" enregistre chiffre");
                            tokenIsSaved = true;
                        }
                    }
                    if(Character.isLetterOrDigit(ch) && !tokenIsSaved) { // un mot peut avoir des chiffres à l'intérieur
                        while(Character.isDigit(ch) || Character.isLetter(ch)){
                            tokenPart += ch;
                            i++;
                            if(i == data.length()) break;
                            ch = data.charAt(i);
                        }
                        if(Character.isLetterOrDigit(ch) && !tokenIsSaved || Character.isLetterOrDigit(data.charAt(i) - 1) && specialChars.indexOf(data.charAt(i)) >= 0 && !tokenIsSaved ) {
                            System.out.println("enregistre mot");
                            tokenList.add(new Token("entier",tokenPart));
                            System.out.println(tokenPart);
                            System.out.println("enregistre mot");
                            tokenIsSaved = true;
                        }
                    }

                    if( specialChars.indexOf(ch) >= 0 && !tokenIsSaved) {
                        System.out.println("special: " + ch);

                    }

//                    if(Character.is)

//                    // Ici on peut accéder à tous les char
//                    if(!Character.isWhitespace(ch)){
//                        tokenPart += ch;
//                        System.out.println(data.charAt(i));
//                    }
//                    if(Character.isWhitespace(ch)) {
//                        if(data.charAt(i+1) ==){
//
//                        }
//                        System.out.println(tokenPart);
//                        tokenList.add(new Token("entier", tokenPart));
//                        tokenPart ="";
//                        System.out.println(tokenList.get(0).getTokenValue());
//                    }
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
