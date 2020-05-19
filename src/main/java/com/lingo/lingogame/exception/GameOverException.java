package com.lingo.lingogame.exception;

public class GameOverException extends Exception {

    public GameOverException() {
        super("This game is already finished.");
    }
}
