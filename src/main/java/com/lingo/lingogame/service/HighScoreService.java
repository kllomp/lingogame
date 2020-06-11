package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.HighScore;
import com.lingo.lingogame.repository.GameRepository;
import com.lingo.lingogame.repository.HighScoreRepository;
import com.lingo.lingogame.service.dto.HighScoreDto;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HighScoreService {

    private HighScoreRepository highScoreRepository;
    private GameRepository gameRepository;

    public HighScoreService(HighScoreRepository repository, GameRepository gameRepository) {
        this.highScoreRepository = repository;
        this.gameRepository = gameRepository;
    }

    public List<HighScore> getHighScores() {
        return highScoreRepository.findAll(Sort.by(Sort.Direction.DESC, "score"));
    }

    public HighScoreDto addHighScore(Long gameId, String username) {
        Game game = gameRepository.getOne(gameId);

        HighScore highScore;

        if(game.getHighScore() == null) {
            highScore = HighScore.forGame(game, username);
            highScoreRepository.save(highScore);
        } else {
            highScore = game.getHighScore();
        }

        return new HighScoreDto(highScore);
    }
}
