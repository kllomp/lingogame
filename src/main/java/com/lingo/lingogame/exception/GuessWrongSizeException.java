package com.lingo.lingogame.exception;

public class GuessWrongSizeException extends Exception{

    public GuessWrongSizeException() {
        super("The guess is not the same size as the correct word");
    }

}
