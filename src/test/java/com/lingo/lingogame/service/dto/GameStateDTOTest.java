package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GameStateDTOTest {

    @Test
    void validateConstructor() throws TimesUpException, GuessWrongSizeException, GameOverException {
        Game g = new Game(new Word("tester"));
        g.setId(1L);
        g.newRound("testen");
        GameStateDTO dto = new GameStateDTO(g);

        assertEquals(g.getId(), dto.getGameId());
        assertEquals(g.getWordProgress(), dto.getProgress());
        assertEquals(g.getRounds().size(), dto.getRounds().size());
        assertEquals(g.isFinished(), dto.isFinished());
    }
}