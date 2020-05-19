package com.lingo.lingogame.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackTest {

    @Test
    void feedBackConstructor() {
        Feedback fb = new Feedback(0, 'a', FeedbackType.CORRECT, null);

        assertEquals(0, fb.getId());
        assertEquals(0, fb.getIndex());
        assertEquals('a', fb.getCharacter());
        assertEquals(FeedbackType.CORRECT, fb.getFeedbackType());
        assertEquals(null, fb.getRound());
    }

    @Test
    void feedbackSetters() {
        Feedback fb = new Feedback();
        fb.setCharacter('a');
        fb.setIndex(123);
        fb.setId(1l);
        fb.setFeedbackType(FeedbackType.NOT_IN_WORD);

        assertEquals(1l, fb.getId());
        assertEquals(123, fb.getIndex());
        assertEquals('a', fb.getCharacter());
        assertEquals(FeedbackType.NOT_IN_WORD, fb.getFeedbackType());
        assertEquals(null, fb.getRound());
    }

    @Test
    void getIndex() {
        Feedback fb = new Feedback();
        assertEquals(0, fb.getIndex());
    }
}