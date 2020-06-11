package com.lingo.lingogame.controller;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.HighScore;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.service.HighScoreService;
import com.lingo.lingogame.service.dto.HighScoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HighScoreControllerTest {

    @Mock
    HighScoreService highScoreService;

    @Test
    void getHighScoresUsesService() {
        HighScore h1 = new HighScore(10, "test");
        HighScore h2 = new HighScore(100, "test2");
        h1.setGame(new Game(new Word("tester")));
        h2.setGame(new Game(new Word("testen")));

        when(highScoreService.getHighScores()).thenReturn(List.of(h1, h2));

        HighScoreController highScoreController = new HighScoreController(highScoreService);

        List<HighScoreDto> scores = highScoreController.getHighscores();

        verify(highScoreService, times(1)).getHighScores();
        assertEquals(2, scores.size());
    }

    @Test
    void addHighScoreUsesService() {
        when(highScoreService.addHighScore(anyLong(), anyString())).thenReturn(new HighScoreDto("username", 1L));

        HighScoreController highScoreController = new HighScoreController(highScoreService);
        HighScoreDto highScoreDto = highScoreController.addHighScore(new HighScoreDto("username", 1L));

        assertEquals("username", highScoreDto.getUsername());
        assertEquals(1L, highScoreDto.getGameId());
        verify(highScoreService, times(1)).addHighScore(anyLong(), anyString());
    }
}