package com.lingo.lingogame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;

class RoundTest {

    @Test
    void roundCreate() {
        Round r = new Round();

        assertNull(r.getGuess());
    }
}