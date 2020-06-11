package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.HighScore;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import com.lingo.lingogame.repository.GameRepository;
import com.lingo.lingogame.repository.HighScoreRepository;
import com.lingo.lingogame.service.dto.HighScoreDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HighScoreServiceTest {

    @Mock
    HighScoreRepository highScoreRepository;
    @Mock
    GameRepository gameRepository;

    @Test
    void getHighScoresUsesRepository() {
        when(highScoreRepository.findAll(any(Sort.class))).thenReturn(List.of(new HighScore(10, "test"), new HighScore(20, "testern")));

        HighScoreService service = new HighScoreService(highScoreRepository, gameRepository);

        List<HighScore> highScores = service.getHighScores();

        verify(highScoreRepository, times(1)).findAll(any(Sort.class));
        assertEquals(2, highScores.size());
    }

    @Test
    void addHighScoreCallsRepository() throws TimesUpException, GuessWrongSizeException, GameOverException {
        Game g = new Game(new Word("tester"));
        g.setId(143L);
        g.newRound("tester");
        when(gameRepository.getOne(anyLong())).thenReturn(g);
        when(highScoreRepository.save(any(HighScore.class))).then(returnsFirstArg());

        HighScoreService service = new HighScoreService(highScoreRepository, gameRepository);

        HighScoreDto result = service.addHighScore(10L, "test");

        assertEquals(143L, result.getGameId());
        assertEquals("test", result.getUsername());
    }

    @Test
    void gameWithHighscoreDoesNotMakeNewHighscore() {
        Game g = new Game(new Word("testen"));
        g.setHighScore(new HighScore(100, "user"));
        g.getHighScore().setGame(g);

        when(gameRepository.getOne(anyLong())).thenReturn(g);

        HighScoreService service = new HighScoreService(highScoreRepository, gameRepository);
        HighScoreDto highScoreDto = service.addHighScore(1L, "Username");

        assertEquals("user", highScoreDto.getUsername());
        assertEquals(100, highScoreDto.getScore());
    }
}