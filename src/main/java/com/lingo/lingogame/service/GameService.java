package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.Round;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.repository.GameRepository;
import com.lingo.lingogame.repository.RoundRepository;
import org.springframework.stereotype.Service;

import java.util.InvalidPropertiesFormatException;

@Service
public class GameService {

    private WordService wordService;
    private GameRepository gameRepository;
    private RoundRepository roundRepository;

    public GameService(WordService wordService, GameRepository gameRepository, RoundRepository roundRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
        this.roundRepository = roundRepository;
    }

    public Game startGame(int wordLength) {
        Game game = new Game(wordService.getRandomWord(wordLength));
        game = gameRepository.save(game);

        return game;
    }

    public Game addGuess(Game game, String guessWord) throws GuessWrongSizeException, GameOverException, InvalidPropertiesFormatException {
        if (!wordService.isValidWord(guessWord)) {
            throw new InvalidPropertiesFormatException(guessWord);
        }

        Round r = game.newRound(guessWord);
        roundRepository.save(r);

        return game;
    }

    public Game getGameById(Long gameId) {
        return gameRepository.getOne(gameId);
    }
}
