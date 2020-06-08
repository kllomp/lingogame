package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.Feedback;
import com.lingo.lingogame.domain.FeedbackType;
import com.lingo.lingogame.domain.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FeedbackDTOTest {

    @Test
    void validateConstructor() {
        FeedbackDTO fb = new FeedbackDTO(new Feedback(0, 'a', FeedbackType.NOT_IN_WORD, null));

        assertEquals(0, fb.getIndex());
        assertEquals('a', fb.getCharacter());
        assertEquals(FeedbackType.NOT_IN_WORD, fb.getFeedbackType());

    }
}