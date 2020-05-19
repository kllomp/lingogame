package com.lingo.lingogame.controller.dto;

import com.lingo.lingogame.domain.Game;

import java.util.List;
import java.util.stream.Collectors;

public class GameStateDTO {

    private long gameId;
    private boolean isFinished;
    private String progress;
    private List<RoundDTO> rounds;

    public GameStateDTO(Game game) {
        gameId = game.getId();
        isFinished = game.isFinished();
        progress = game.getWordProgress();
        rounds = game.getRounds().stream().map(RoundDTO::new).collect(Collectors.toList());
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
}
