package com.lingo.lingogame.domain;

import javax.persistence.*;
import java.util.stream.IntStream;

@Entity
public class HighScore {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    private Game game;

    private int score = 0;

    public static HighScore forGame(Game game, String username) {
        int score = 0;

        if(!game.isFinished()) {
            return null;
        }

        if(!game.isWon()) {
            return null;
        }

        score += 50 - game.getRounds().size() * 10;

        score += game.getRounds().stream().flatMapToInt(round ->
           IntStream.of(round.getFeedbackList().stream().flatMapToInt(feedback -> {
               if(feedback.getFeedbackType().equals(FeedbackType.WRONG_PLACE)) {
                   return IntStream.of(1);
               } else if (feedback.getFeedbackType().equals(FeedbackType.CORRECT)) {
                   return IntStream.of(2);
               }
               return IntStream.of(0);
           }).sum())
        ).sum();

        HighScore highScore = new HighScore(score, username);
        highScore.setGame(game);
        return highScore;
    }

    public HighScore(int score, String username) {
        this.score = score;
        this.username = username;
    }

    public HighScore() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }
}
