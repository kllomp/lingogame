package com.lingo.lingogame.domain;

import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HighScoreTest {

    @Test
    void validateConstructor() {
        HighScore highScore = new HighScore(215, "user");

        assertEquals(215, highScore.getScore());
        assertEquals("user", highScore.getUsername());
    }

    @Test
    void createHighscoreForFinishedGame() throws TimesUpException, GuessWrongSizeException, GameOverException {
        Game game = new Game(new Word("tester"));
        game.newRound("tester");

        HighScore result = HighScore.forGame(game, "test");

        assertNotNull(result);
        assertEquals(52, result.getScore());
    }

    @Test
    void createHighscoreForLongGame() throws TimesUpException, GuessWrongSizeException, GameOverException {
        Game game = new Game(new Word("kaart"));
        game.newRound("karel");
        game.newRound("kaars");
        game.newRound("kraak");
        game.newRound("klaar");
        game.newRound("kaart");

        HighScore result = HighScore.forGame(game, "test");

        assertEquals(35, result.getScore());
    }

    @Test
    void highscoreIsZeroWhenNotGuessedCorrectly() throws TimesUpException, GuessWrongSizeException, GameOverException {
        Game game = new Game(new Word("kaart"));
        game.newRound("karel");
        game.newRound("kaars");
        game.newRound("kraak");
        game.newRound("klaar");
        game.newRound("klaar");

        HighScore result = HighScore.forGame(game, "test");

       assertNull(result);
    }

    @Test
    void getNullWhenGameIsNogFinished() {
        Game game = new Game(new Word("kaart"));

        HighScore result = HighScore.forGame(game, "test");

        assertNull(result);
    }
}