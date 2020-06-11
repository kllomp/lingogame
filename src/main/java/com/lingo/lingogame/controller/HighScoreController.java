package com.lingo.lingogame.controller;

import com.lingo.lingogame.service.HighScoreService;
import com.lingo.lingogame.service.dto.HighScoreDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/highscore")
@CrossOrigin(origins = "*")
public class HighScoreController {

    private HighScoreService highScoreService;

    public HighScoreController(HighScoreService highScoreService) {
        this.highScoreService = highScoreService;
    }

    @GetMapping
    public List<HighScoreDto> getHighscores() {
        return highScoreService.getHighScores().stream().map(HighScoreDto::new).collect(Collectors.toList());
    }

    @PostMapping
    public HighScoreDto addHighScore(@RequestBody HighScoreDto highScoreDto) {
        return highScoreService.addHighScore(highScoreDto.getGameId(), highScoreDto.getUsername());
    }
}
