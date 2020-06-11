package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.HighScore;

public class HighScoreDto {

    private int score;
    private String username;
    private Long gameId;

    public HighScoreDto(String username, Long gameId) {
        this.username = username;
        this.gameId = gameId;
    }

    public HighScoreDto(HighScore highScore) {
        score = highScore.getScore();
        username = highScore.getUsername();
        gameId = highScore.getGame().getId();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
