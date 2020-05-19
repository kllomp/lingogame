package com.lingo.lingogame.controller.dto;

import com.lingo.lingogame.domain.Round;

import java.util.List;
import java.util.stream.Collectors;

public class RoundDTO {

    private int index;
    private String guess;
    private List<FeedbackDTO> feedbackList;

    public RoundDTO(int index, String guess, List<FeedbackDTO> feedbackList) {
        this.index = index;
        this.guess = guess;
        this.feedbackList = feedbackList;
    }

    public RoundDTO(Round round) {
        this.index = round.getIndex();
        this.guess = round.getGuess();
        this.feedbackList = round.getFeedbackList().stream().map(feedback -> new FeedbackDTO(feedback)).collect(Collectors.toList());
    }

    public int getIndex() {
        return index;
    }

    public String getGuess() {
        return guess;
    }

    public List<FeedbackDTO> getFeedbackList() {
        return feedbackList;
    }
}
