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
                            !specialCharacter.isSpecialCharacter(String.valueOf(data.charAt(i))) &&
                            !(data.charAt(i) == '.')){
                        throw new Exception("IllegalCharacter",String.valueOf(data.charAt(i)));
                    }
                    // Ici on s'assure que le charatère '.' soit entouré de chiffres.
                    // Quand c'est le cas alors on l'ajoute au nombre.
                    else if(data.charAt(i) == '.' &&
                            i > 0 &&
                            i < data.length()-1 &&
                            Character.isDigit(data.charAt(i-1)) &&
                            Character.isDigit(data.charAt(i+1))){
                        str.append(data.charAt(i));
                    }
//                    if(data.length() == i + 1){
//                        tokenList.add(new Token("endLine"));
//                    }
                }
            }
            reader.close();
            tokenList.forEach(token -> System.out.println(token.getTokenType()));
            System.out.println("---------------------");
            return tokenList;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
