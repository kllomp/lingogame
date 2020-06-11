package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.HighScore;
import com.lingo.lingogame.domain.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HighScoreDtoTest {

    @Test
    void validateConstrucor() {
        HighScoreDto dto = new HighScoreDto("test", 10L);

        assertEquals("test", dto.getUsername());
        assertEquals(10L, dto.getGameId());
    }

    @Test
    void validateObjectConstructor() {
        HighScore hs = new HighScore(100, "user");
        hs.setGame(new Game(new Word("tester")));
        HighScoreDto dto = new HighScoreDto(hs);

        assertEquals("user", dto.getUsername());
        assertEquals(100, dto.getScore());
    }

    @Test
    void validateSetters() {
        HighScoreDto hs = new HighScoreDto("user", 1l);
        hs.setUsername("username");
        hs.setScore(100);
        hs.setGameId(135089l);

        assertEquals("username", hs.getUsername());
        assertEquals(100, hs.getScore());
        assertEquals(135089L, hs.getGameId());
    }
}