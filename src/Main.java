import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {
    public static void main(String[] args) {
        File textFile = new File("sourceCode.txt");
        Parser parser = new Parser(textFile);
    }
}
