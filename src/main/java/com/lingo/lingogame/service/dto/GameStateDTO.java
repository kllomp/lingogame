package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameStateDTO {

    private long gameId;
    private boolean isFinished;
    private boolean isWon;
    private String progress;
    private List<RoundDTO> rounds;

    public GameStateDTO(Game game) {
        this.gameId = game.getId();
        this.isFinished = game.isFinished();
        this.progress = game.getWordProgress();
        this.isWon = game.isWon();
        this.rounds = game.getRounds().stream().map(RoundDTO::new).collect(Collectors.toList());
    }

    public long getGameId() {
        return gameId;
    }

    public List<RoundDTO> getRounds() {
        return rounds;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public String getProgress() {
        return progress;
    }

    public boolean isWon() {
        return isWon;
    }
}
