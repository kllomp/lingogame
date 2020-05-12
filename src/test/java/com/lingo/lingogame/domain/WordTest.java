package com.lingo.lingogame.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Word")
public class WordTest {

    @Test
    @DisplayName("Length is set by constructor")
    void lengthIsSetByConstructor() {
        Word w = new Word("garage", "NL");
        assertEquals(6, w.getLength());
    }

    @Test
    @DisplayName("Length is set by setting the word")
    void lengthIsSetBySetWord() {
        Word w = new Word("a", "NL");
        w.setWord("stoel");
        assertEquals(5, w.getLength());
    }

    @Test
    @DisplayName("Correct word is valid")
    void correctWordIsValid() {
        Word w = new Word("garage");
        Boolean result = w.isValid();
        assertEquals(true, result);
    }

    @Test
    @DisplayName("Word is invalid when all lowercase")
    void incorrectWordWithUpperCase() {
        Word w = new Word("Garage");
        Boolean result = w.isValid();
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Word is invalid when containing number")
    void incorrectWordWithNumber() {
        Word w = new Word("g4rage");
        Boolean result = w.isValid();
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Word is invalid when containing special character")
    void incorrectWordWithSpecialCharacter() {
        Word w = new Word("garag/e");
        Boolean result = w.isValid();
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Word is invalid when to short")
    void incorrectShortWord() {
        Word w = new Word("weg");
        Boolean result = w.isValid();
        assertEquals(false, result);
    }

    @Test
    @DisplayName("Word is invalid when to long")
    void incorrectLongWord() {
        Word w = new Word("snelwegen");
        Boolean result = w.isValid();
        assertEquals(false, result);
    }
}