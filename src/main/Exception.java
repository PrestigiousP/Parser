package main;

public class Exception extends Error{
    public Exception(String errorMessage) {
        super(errorMessage);
    }
    public Exception(String message, Throwable cause) {super(message, cause);}

    public Exception(String errorMessage, String cause) { super(errorMessage + " " + cause); }

    public Exception(String errorMessage, int valeur) { super(errorMessage + " " + valeur); }

}
// TODO: à tester
