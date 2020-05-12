package com.lingo.lingogame.controller;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.repository.WordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
@ExtendWith(MockitoExtension.class)
class WordControllerTest {

    @Mock
    WordRepository repository;

    @Test
    void insertWords() {
        WordController controller = new WordController(repository);

        Word w1 = new Word("garage");
        Word w2 = new Word("stoel");
        Word w3 = new Word("horloge");
        List<Word> words = new ArrayList<>();
        words.add(w1);
        words.add(w2);
        words.add(w3);

        controller.insertWords(words);

        verify(repository, times(1)).saveAll(anyList());
    }
}