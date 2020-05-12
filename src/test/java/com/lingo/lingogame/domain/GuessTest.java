package com.lingo.lingogame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuessTest {

    @Test
    public void makeEmptyGuess() {
        Guess guess = new Guess();

        guess.setId(1l);
        Long id = guess.getId();

        assertEquals(1l, id);
    }

}