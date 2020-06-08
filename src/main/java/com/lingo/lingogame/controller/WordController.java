package com.lingo.lingogame.controller;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.service.WordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/words")
public class WordController {

    private final WordService wordService;

    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @PostMapping
    public ResponseEntity<List<Word>> insertWords(@RequestBody List<Word> words) {
        this.wordService.insertWords(words);
        return new ResponseEntity<>(words, HttpStatus.CREATED);
    }

}
