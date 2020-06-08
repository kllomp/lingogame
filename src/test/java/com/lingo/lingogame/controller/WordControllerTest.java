package com.lingo.lingogame.controller;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.service.WordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class WordControllerTest {

    @Mock
    private WordService service;

    @Test
    void insertWords() {
        List<Word> words = new ArrayList<>();
        WordController wordController = new WordController(service);
        ResponseEntity<List<Word>> response = wordController.insertWords(words);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(words, response.getBody());
        verify(service, times(1)).insertWords(anyList());
    }
}