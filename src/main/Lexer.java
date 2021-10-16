package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Lexer {
    private ArrayList<Token> tokenList;
    private final SpecialCharacter specialCharacter;
    private final File sourceCode;

    public Lexer(File textFile, ArrayList<Token> tokenList, SpecialCharacter specialCharacter){
        sourceCode = textFile;
        this.tokenList = tokenList;
        this.specialCharacter = specialCharacter;
    }

    public ArrayList<Token> tokenize(){
        try{
            Scanner reader = new Scanner(sourceCode);
            ArrayList<Token> tokenList = new ArrayList<>();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                StringBuilder str = new StringBuilder();
                for(int i=0;i<data.length();i++){
                    char ch = data.charAt(i);
                    if(Character.isWhitespace(data.charAt(i)) && !str.isEmpty()){
                        tokenList.add(new Token(str.toString()));
                        str.setLength(0);
                    }
                    else if(specialCharacter.isSpecialCharacter(String.valueOf(data.charAt(i)))){
                        if(!str.isEmpty()){
                            tokenList.add(new Token(str.toString()));
                            str.setLength(0);
                        }
                        tokenList.add(new Token(String.valueOf(data.charAt(i))));
                    }
                    else if(Character.isLetter(data.charAt(i)) ||
                            Character.isDigit(data.charAt(i)) ||
                            String.valueOf(data.charAt(i)).equals("_")){
                        str.append(data.charAt(i));
                        if(i+1 == data.length()){
                            tokenList.add(new Token(str.toString()));
                        }
                    }
                    else if(!Character.isWhitespace(data.charAt(i)) &&
                            !specialCharacter.isSpecialCharacter(String.valueOf(data.charAt(i)))){
                        throw new IllegalCharacter(String.valueOf(data.charAt(i)));
                    }
                }
            }
            reader.close();
            tokenList.forEach(token -> {
                System.out.println(token.getTokenType());
            });
//            System.out.println(tokenList);
            return tokenList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch(IllegalCharacter e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Token> getTokenList(){
        return this.tokenList;
    }
}
