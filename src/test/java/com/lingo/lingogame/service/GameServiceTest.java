package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.GuessResult;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private WordService wordService;

    @Test
    void startGame() {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));

        GameService gameService = new GameService(wordService);

        Game game = gameService.startGame(6);

        assertEquals(0, game.getGuesses().size());
        verify(wordService, times(1)).getRandomWord(anyInt());
    }

    @Test
    void doCorrectGuess() throws GuessWrongSizeException {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));

        GameService gameService = new GameService(wordService);
        Game game = gameService.startGame(6);

        List<GuessResult> results = gameService.addGuess(game, "tester");

        assertEquals(true, results.contains(GuessResult.CORRECT));
        assertEquals(false, results.contains(GuessResult.NOT_IN_WORD));
        assertEquals(false, results.contains(GuessResult.WRONG_PLACE));
    }
}