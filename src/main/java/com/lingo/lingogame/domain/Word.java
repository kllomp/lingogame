package com.lingo.lingogame.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Entity
public class Word {

    @JsonIgnore
    private static final int MIN_LENGTH = 5;
    @JsonIgnore
    private static final int MAX_LENGTH = 7;
    @JsonIgnore
    private static final String ALLOWED_REGEX = "[a-z]+\\.?";

    @Id
    @Column(unique = true)
    private String word;

    private int length;
    private String language = "NL";

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "correctWord")
    private Set<Game> games = new HashSet<>();

    @JsonIgnore
    @Transient
    private final List<Predicate<Word>> validators = new LinkedList<>();

    public Word(String word, String language) {
        this.word = word;
        this.language = language;
        this.length = word.length();
        initializeValidators();
    }

    public Word(String word) {
        this.word = word;
        this.length = word.length();
        initializeValidators();
    }

    public Word() {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
        this.length = word.length();
    }

    public int getLength() {
        return length;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Set<Game> getGames() {
        return games;
    }

    public boolean isValid() {
        return validators.stream().allMatch(wordPredicate -> wordPredicate.test(this));
    }

    private void initializeValidators() {
        this.validators.add(Word::isBetweenAllowedLength);
        this.validators.add(Word::isAllowedCharacters);
    }

    private static boolean isBetweenAllowedLength(Word word) {
        return MIN_LENGTH <= word.getLength() && word.getLength() <= MAX_LENGTH;
    }

    private static boolean isAllowedCharacters(Word word) {
        return word.getWord().matches(ALLOWED_REGEX);
    }

}
