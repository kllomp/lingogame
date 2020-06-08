package com.lingo.lingogame.service.dto;

public class GuessDTO {

    private int gameId;
    private String guess;

    public GuessDTO(int gameId, String guess) {
        this.gameId = gameId;
        this.guess = guess;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGuess() {
        return guess;
    }
}
