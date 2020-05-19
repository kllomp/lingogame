package com.lingo.lingogame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackTest {

    @Test
    void getIndex() {
        Feedback fb = new Feedback();
        assertEquals(0, fb.getIndex());
    }
}