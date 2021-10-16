package main;

import main.Parser;

import java.io.File;  // Import the File class


public class Main {
    public static void main(String[] args) {
        File textFile = new File("sourceCode.txt");
        Parser parser = new Parser(textFile);
    }
}
