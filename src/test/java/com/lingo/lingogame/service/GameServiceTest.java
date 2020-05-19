package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.FeedbackType;
import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.repository.GameRepository;
import com.lingo.lingogame.repository.RoundRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InvalidPropertiesFormatException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private WordService wordService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @Test
    void startGame() {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1L);
            return g;
        });
        GameService gameService = new GameService(wordService, gameRepository, roundRepository);

        Game game = gameService.startGame(6);

        assertEquals(false, game.isFinished());
        assertEquals(0, game.getRounds().size());
        verify(wordService, times(1)).getRandomWord(anyInt());
        verify(gameRepository, times(1)).save(any(Game.class));
    }

    @Test
    void doCorrectGuess() throws GuessWrongSizeException, GameOverException, InvalidPropertiesFormatException {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(wordService.isValidWord(any())).thenReturn(true);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1L);
            return g;
        });

        GameService gameService = new GameService(wordService, gameRepository, roundRepository);
        Game game = gameService.startGame(6);

        game = gameService.addGuess(game, "tester");

        assertEquals("tester", game.getWordProgress());
        assertTrue(game.isFinished());
        assertEquals(0, game.getRounds().iterator().next().getFeedbackList().stream().filter(
                feedback -> !feedback.getFeedbackType().equals(FeedbackType.CORRECT))
                .count());
    }

    @Test
    void doPartialCorrectGuess() throws InvalidPropertiesFormatException, GuessWrongSizeException, GameOverException {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(wordService.isValidWord(any())).thenReturn(true);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1l);
            return g;
        });

        GameService gameService = new GameService(wordService, gameRepository, roundRepository);
        Game game = gameService.startGame(6);

        game = gameService.addGuess(game, "testen");

        assertEquals("teste.", game.getWordProgress());
        assertEquals(1, game.getRounds().iterator().next().getFeedbackList().stream().filter(
                feedback -> !feedback.getFeedbackType().equals(FeedbackType.CORRECT))
                .collect(Collectors.toList())
                .size());
    }

    @Test
    void doInvalidGuessTooLong() {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(wordService.isValidWord(any())).thenReturn(true);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1l);
            return g;
        });

        GameService gameService = new GameService(wordService, gameRepository, roundRepository);
        Game game = gameService.startGame(6);

        assertThrows(GuessWrongSizeException.class, () -> {
            gameService.addGuess(game, "testen123");
        });
    }

    @Test
    void doInvalidGuess() {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(wordService.isValidWord(any())).thenReturn(false);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1l);
            return g;
        });

        GameService gameService = new GameService(wordService, gameRepository, roundRepository);
        Game game = gameService.startGame(6);

        assertThrows(InvalidPropertiesFormatException.class, () -> {
            gameService.addGuess(game, "test3r");
        });
    }

    @Test
    void doGuessWithInvalidStartCharacter() {
        when(wordService.getRandomWord(6)).thenReturn(new Word("tester"));
        when(wordService.isValidWord(any())).thenReturn(false);
        when(gameRepository.save(any(Game.class))).thenAnswer(invocationOnMock -> {
            Game g = invocationOnMock.getArgument(0);
            g.setId(1l);
            return g;
        });

        GameService gameService = new GameService(wordService, gameRepository, roundRepository);
        Game game = gameService.startGame(6);

        assertThrows(InvalidPropertiesFormatException.class, () -> {
            gameService.addGuess(game, "pester");
        });
    }

}