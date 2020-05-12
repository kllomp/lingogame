package com.lingo.lingogame.domain;

import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.*;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;


    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Word correctWord;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game")
    private List<Guess> guesses;

    public Game(Word correctWord) {
        this.correctWord = correctWord;
        this.guesses = new ArrayList<>();
    }

    public Game() {
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public List<GuessResult> addGuess(Word w) throws GuessWrongSizeException {
        if(w.getWord().length() != correctWord.getWord().length()) {
            throw new GuessWrongSizeException();
        }

        Guess guess = new Guess(w.getWord());

        List<GuessResult> result = guess.getResult(correctWord.getWord());

        guesses.add(guess);

        return result;
    }

    public Boolean isFinished() {
        if(guesses.size() == 0) {
            return false;
        }
        return guesses.get(guesses.size() -1).getGuess().equals(correctWord.getWord());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
