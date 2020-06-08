package com.lingo.lingogame.service;

import com.lingo.lingogame.domain.Word;
import com.lingo.lingogame.repository.WordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WordService {

    private WordRepository repository;

    public WordService(WordRepository repository) {
        this.repository = repository;
    }

    public Word getRandomWord(int wordLength) {
        Random random = new Random();
        if (wordLength == 0) {
            wordLength = 5 + random.nextInt(3);
        }
        List<Word> words = repository.findByLength(wordLength);

        return words.get(random.nextInt(words.size()));
    }

    public void insertWords(List<Word> words) {
        repository.saveAll(words);
    }

    public boolean isValidWord(String guessWord) {
        Word w = new Word(guessWord);

        return w.isValid() && repository.findById(guessWord).isPresent();
    }
}
