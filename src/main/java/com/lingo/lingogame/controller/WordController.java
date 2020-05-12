package com.lingo.lingogame.controller;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.repository.WordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "words")
public class WordController {

    private final WordRepository wordRepository;

    public WordController(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @PostMapping
    public ResponseEntity insertWords(@RequestBody List<Word> words) {
        this.wordRepository.saveAll(words);
        return new ResponseEntity(words, HttpStatus.CREATED);
    }

}
