package com.lingo.lingogame.domain;

import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import com.sun.istack.NotNull;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Entity
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Word correctWord;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "game", cascade = CascadeType.PERSIST)
    private Set<Round> rounds;
    private String wordProgress;

    private LocalDateTime lastGuess;

    public Game(Word correctWord) {
        this.correctWord = correctWord;
        this.rounds = new HashSet<>();
        this.wordProgress = correctWord.getWord().charAt(0) +
                IntStream.range(0, correctWord.getWord().length() - 1).mapToObj(i -> ".").collect(Collectors.joining(""));
    }

    public Game() {
    }

    public Round newRound(String guessWord) throws GuessWrongSizeException, GameOverException, TimesUpException {
        if (guessWord.length() != correctWord.getWord().length()) {
            throw new GuessWrongSizeException();
        }
        LocalDateTime now = LocalDateTime.now();

        if(lastGuess != null && lastGuess.compareTo(now.minusSeconds(10)) < 0) {
            throw new TimesUpException();
        }
        if (isFinished()) {
            throw new GameOverException();
        }

        Round round = new Round(guessWord, correctWord.getWord());
        round.setIndex(rounds.size());
        round.setGame(this);
        rounds.add(round);

        round.getFeedbackList().stream().forEach(feedback -> {
            if (feedback.getFeedbackType() == FeedbackType.CORRECT) {
                char[] wordProgressArray = this.wordProgress.toCharArray();
                wordProgressArray[feedback.getIndex()] = feedback.getCharacter();
                wordProgress = String.valueOf(wordProgressArray);
            }
        });

        lastGuess = LocalDateTime.now();

        return round;
    }

    public Boolean isFinished() {
        if (rounds.size() >= 5) {
            return true;
        }

        Optional<Round> maxRound = rounds.stream().max(Comparator.comparing(Round::getIndex));
        if (maxRound.isEmpty()) {
            return false;
        }

        return maxRound.get().getGuess().equals(this.correctWord.getWord());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Round> getRounds() {
        return rounds;
    }

    public String getWordProgress() {
        return this.wordProgress;
    }
}
