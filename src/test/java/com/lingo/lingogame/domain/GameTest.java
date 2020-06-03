package com.lingo.lingogame.domain;

import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void startGame() {
        Game game = new Game(new Word("garage"));

        assertNotNull(game);
        assertEquals(0, game.getRounds().size());
        assertEquals(false, game.isFinished());
        assertEquals("g.....", game.getWordProgress());
    }

    private static Stream<Arguments> provideWordsAndGuesses() {
        return Stream.of(
                Arguments.of("garage", "garage", true, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT}),
                Arguments.of("abcdef", "ahijkl", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD}),
                Arguments.of("garage", "garagz", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD}),
                Arguments.of("garage", "gxaxxx", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD, FeedbackType.WRONG_PLACE, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD}),
                Arguments.of("garage", "gxaxax", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD, FeedbackType.WRONG_PLACE, FeedbackType.NOT_IN_WORD, FeedbackType.WRONG_PLACE, FeedbackType.NOT_IN_WORD}),
                Arguments.of("axxxx", "azzza", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD}),
                Arguments.of("axxxx", "aazzz", false, new FeedbackType[]{FeedbackType.CORRECT, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD, FeedbackType.NOT_IN_WORD})
        );
    }

    @ParameterizedTest
    @MethodSource("provideWordsAndGuesses")
    void verifyWordsAndGuesses(
            String correctWord,
            String guessWord,
            Boolean checkIsFinished,
            FeedbackType[] correctResults
    ) throws GuessWrongSizeException, GameOverException, TimesUpException {
        Game game = new Game(new Word(correctWord));

        Round round = game.newRound(guessWord);
        Boolean isFinished = game.isFinished();

        assertEquals(guessWord.length(), round.getFeedbackList().size());
        assertEquals(checkIsFinished, isFinished);
        for (int i = 0; i < round.getFeedbackList().size(); i++) {
            Feedback fb = round.getFeedbackList().get(i);
            assertEquals(correctResults[fb.getIndex()], round.getFeedbackList().get(i).getFeedbackType());
        }

    }

    @Test
    public void guessIsWrongSize() {
        Game game = new Game(new Word("garage"));

        assertThrows(GuessWrongSizeException.class, () -> game.newRound("garages"));
    }

    @Test
    public void startWithNotFinished() {
        Game game = new Game(new Word("tester"));
        Boolean isFinished = game.isFinished();

        assertEquals(false, isFinished);
    }

    @Test
    void isFinishedWhenGivenCorrectWord() throws GuessWrongSizeException, GameOverException, TimesUpException {
        Game game = new Game(new Word("kaars"));
        game.newRound("laars");

        assertEquals(false, game.isFinished());

        game.newRound("kaars");

        assertEquals(true, game.isFinished());

    }

    @Test
    void doSlowGuess() throws InterruptedException, TimesUpException, GuessWrongSizeException, GameOverException {
        Game game = new Game(new Word("kaart"));
        game.newRound("kaars", LocalDateTime.now().minusSeconds(11));

        assertThrows(TimesUpException.class, () -> game.newRound("kerel"));

    }
}