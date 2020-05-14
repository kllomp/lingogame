package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    private WordRepository repository;

    @Test
    void getRandomWord() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("kaart"));
        words.add(new Word("kaars"));
        words.add(new Word("wagen"));
        when(repository.findByLength(5)).thenReturn(words);

        WordService wordService = new WordService(repository);
        Word result = wordService.getRandomWord(5);

        assertEquals(5, result.getLength());
    }

    @Test
    void insertWords() {
        WordService wordService = new WordService(repository);

        Word w1 = new Word("garage");
        Word w2 = new Word("stoel");
        Word w3 = new Word("horloge");
        List<Word> words = new ArrayList<>();
        words.add(w1);
        words.add(w2);
        words.add(w3);

        wordService.insertWords(words);

        verify(repository, times(1)).saveAll(anyList());
    }
}