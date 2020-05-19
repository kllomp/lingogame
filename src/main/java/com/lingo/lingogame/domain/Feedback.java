package com.lingo.lingogame.domain;

import javax.persistence.*;

@Entity
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int index;
    private char character;
    private FeedbackType feedbackType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Round round;

    public Feedback() {
    }

    public Feedback(int index, char character, FeedbackType feedbackType, Round round) {
        this.index = index;
        this.character = character;
        this.feedbackType = feedbackType;
        this.round = round;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public FeedbackType getFeedbackType() {
        return this.feedbackType;
    }

    public void setFeedbackType(FeedbackType feedbackType) {
        this.feedbackType = feedbackType;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }
}
