package com.lingo.lingogame.service.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GuessDTOTest {

    @Test
    void validateConstructor() {
        GuessDTO guessDTO = new GuessDTO(1, "guessing");

        assertEquals(1, guessDTO.getGameId());
        assertEquals("guessing", guessDTO.getGuess());
    }
}
