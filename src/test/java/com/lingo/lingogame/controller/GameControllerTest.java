package com.lingo.lingogame.controller;

import com.lingo.lingogame.service.dto.GameStateDTO;
import com.lingo.lingogame.service.dto.GuessDTO;
import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import com.lingo.lingogame.service.GameService;
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
    private GameService gameService;

    @Test
    void startGame() {
        Game g = new Game(new Word("tester"));
        g.setId(0L);
        when(gameService.startGame(anyInt())).thenReturn(g);

        // start game, given length
        GameController controller = new GameController(gameService);
        GameStateDTO gameStateDTO = controller.startGame(5);

        verify(gameService, times(1)).startGame(5);

        assertEquals(0, gameStateDTO.getRounds().size());
        assertFalse(gameStateDTO.isFinished());
    }

    @Test
    void doGuess() throws GuessWrongSizeException, GameOverException, InvalidPropertiesFormatException, TimesUpException {
        Game g = new Game(new Word("tester"));
        g.setId(1L);
        when(gameService.getGameById(anyLong())).thenReturn(g);
        when(gameService.addGuess(any(), any())).thenReturn(g);

        GameController controller = new GameController(gameService);
        GameStateDTO gameStateDTO = controller.doGuess(new GuessDTO(1, "tester"));

        verify(gameService, times(1)).addGuess(any(Game.class), anyString());
        assertNotNull(gameStateDTO);
    }
}