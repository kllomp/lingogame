package com.lingo.lingogame.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Guess {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Game game;

    private String guess;

    public Guess() {}

    public Guess(String guess) {
        this.guess = guess;
    }

    public List<GuessResult> getResult(String correct) {
        List<GuessResult> result = new ArrayList<>();

        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char correctChar = correct.charAt(i);

            if (guessChar == correctChar) {
                result.add(GuessResult.CORRECT);
                correct = correct.replaceFirst(String.valueOf(guessChar), "!");
            } else if (correct.indexOf(guessChar) > -1) {
                result.add(GuessResult.WRONG_PLACE);
                correct = correct.replaceFirst(String.valueOf(guessChar), "!");
            } else {
                result.add(GuessResult.NOT_IN_WORD);
            }
        }

        return result;
    }

    public String getGuess() {
        return guess;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }
}
