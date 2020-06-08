package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.FeedbackType;
import com.lingo.lingogame.domain.Round;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RoundDTOTest {

    @Test
    void testConstructor() {
        RoundDTO round = new RoundDTO(new Round("tester", "tester"));

        assertEquals("tester", round.getGuess());
        assertEquals(6, round.getFeedbackList().size());

        assertEquals(6, round.getFeedbackList().stream().filter(
                feedback -> feedback.getFeedbackType().equals(FeedbackType.CORRECT))
                .count());
    }
}