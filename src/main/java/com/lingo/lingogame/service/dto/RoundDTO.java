package com.lingo.lingogame.service.dto;

import com.lingo.lingogame.domain.Round;

import java.util.List;
import java.util.stream.Collectors;

public class RoundDTO {

    private int index;
    private String guess;
    private List<FeedbackDTO> feedbackList;

    public RoundDTO(Round round) {
        this.index = round.getIndex();
        this.guess = round.getGuess();
        this.feedbackList = round.getFeedbackList().stream().map(FeedbackDTO::new).collect(Collectors.toList());
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
