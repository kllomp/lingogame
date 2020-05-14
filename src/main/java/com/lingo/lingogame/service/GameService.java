package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.GuessResult;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private WordService wordService;

    public GameService(WordService wordService) {
        this.wordService = wordService;
    }

    public Game startGame(int wordLength) {

        Game game = new Game(wordService.getRandomWord(wordLength));

        return game;
    }

    public List<GuessResult> addGuess(Game game, String guessWord) throws GuessWrongSizeException {

        Word word = new Word(guessWord);

        List<GuessResult> guessResults = game.addGuess(word);

        return guessResults;
    }
}
