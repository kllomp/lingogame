package com.lingo.lingogame.controller;

import com.lingo.lingogame.controller.dto.GameStateDTO;
import com.lingo.lingogame.controller.dto.GuessDTO;
import com.lingo.lingogame.controller.dto.RoundDTO;
import com.lingo.lingogame.domain.FeedbackType;
import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import com.lingo.lingogame.repository.GameRepository;
import com.lingo.lingogame.repository.RoundRepository;
import com.lingo.lingogame.service.GameService;
import com.lingo.lingogame.service.WordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.InvalidPropertiesFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private WordService wordService;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private RoundRepository roundRepository;

    @Test
    void startGame() {
        Game g = new Game(new Word("tester"));
        g.setId(0l);
        when(gameRepository.save(any(Game.class))).thenReturn(g);
        when(wordService.getRandomWord(anyInt())).thenReturn(new Word("tester"));

        // start game, given length
        GameController controller = new GameController(new GameService(wordService, gameRepository, roundRepository));
        GameStateDTO gameStateDTO = controller.startGame(5);

        verify(wordService, times(1)).getRandomWord(5);
        verify(gameRepository, times(1)).save(any(Game.class));

        assertNotNull(gameStateDTO.getGameId());
        assertEquals(0, gameStateDTO.getRounds().size());
        assertFalse(gameStateDTO.isFinished());
    }

    @Test
    void doGuess() throws GuessWrongSizeException, GameOverException, InvalidPropertiesFormatException, TimesUpException {
        Game g = new Game(new Word("tester"));
        g.setId(1l);
        when(gameRepository.getOne(any())).thenReturn(g);
        when(wordService.isValidWord(anyString())).thenReturn(true);

        GameController controller = new GameController(new GameService(wordService, gameRepository, roundRepository));
        GameStateDTO gameStateDTO = controller.doGuess(new GuessDTO(1, "tester"));

        assertNotNull(gameStateDTO);
        assertEquals(1, gameStateDTO.getRounds().size());
        assertTrue(gameStateDTO.isFinished());
        assertEquals("tester", gameStateDTO.getRounds().get(0).getGuess());

        RoundDTO roundDto = gameStateDTO.getRounds().get(0);
        roundDto.getFeedbackList().stream().forEach(feedbackDTO -> {
            assertEquals(FeedbackType.CORRECT, feedbackDTO.getFeedbackType());
        });
    }
}