package com.lingo.lingogame.domain;

import com.lingo.lingogame.exception.GuessWrongSizeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void startGame() {
        Game game = new Game(new Word("garage"));

        assertNotNull(game);
        assertEquals(0, game.getGuesses().size());
    }

    private static Stream<Arguments> provideWordsAndGuesses() {
        return Stream.of(
                Arguments.of("garage", "garage", true, new GuessResult[]{GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT}),
                Arguments.of("abcdef", "ghijkl", false, new GuessResult[]{GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD}),
                Arguments.of("garage", "garagz", false, new GuessResult[]{GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.CORRECT, GuessResult.NOT_IN_WORD}),
                Arguments.of("garage", "xxaxxx", false, new GuessResult[]{GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.WRONG_PLACE, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD}),
                Arguments.of("garage", "xxaxax", false, new GuessResult[]{GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.WRONG_PLACE, GuessResult.NOT_IN_WORD, GuessResult.WRONG_PLACE, GuessResult.NOT_IN_WORD}),
                Arguments.of("axxxx", "azzza", false, new GuessResult[]{GuessResult.CORRECT, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD}),
                Arguments.of("axxxx", "aazzz", false, new GuessResult[]{GuessResult.CORRECT, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD, GuessResult.NOT_IN_WORD})
        );
    }

    @ParameterizedTest
    @MethodSource("provideWordsAndGuesses")
    void verifyWordsAndGuesses(
            String correctWord,
            String guessWord,
            Boolean checkIsFinished,
            GuessResult[] correctResults
    ) throws GuessWrongSizeException {
        Game game = new Game(new Word(correctWord));

        List<GuessResult> result = game.addGuess(new Word(guessWord));
        Boolean isFinished = game.isFinished();

        assertEquals(guessWord.length(), result.size());
        assertEquals(checkIsFinished, isFinished);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(correctResults[i], result.get(i));
        }

    }

    @Test
    public void guessIsWrongSize() {
        Game game = new Game(new Word("garage"));

        assertThrows(GuessWrongSizeException.class, () -> game.addGuess(new Word("garages")));
    }

    @Test
    public void startWithNotFinished() {
        Game game = new Game(new Word("tester"));
        Boolean isFinished = game.isFinished();

        assertEquals(false, isFinished);
    }
}