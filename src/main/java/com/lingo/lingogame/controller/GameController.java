package com.lingo.lingogame.controller;

import com.lingo.lingogame.controller.dto.GameStateDTO;
import com.lingo.lingogame.controller.dto.GuessDTO;
import com.lingo.lingogame.domain.Game;
import com.lingo.lingogame.exception.GameOverException;
import com.lingo.lingogame.exception.GuessWrongSizeException;
import com.lingo.lingogame.exception.TimesUpException;
import com.lingo.lingogame.service.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.InvalidPropertiesFormatException;

@RestController
@RequestMapping(value = "/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/start")
    public GameStateDTO startGame(@RequestParam int wordLength) {
        Game game = gameService.startGame(wordLength);

        return new GameStateDTO(game);
    }

    @PostMapping("/guess")
    public GameStateDTO doGuess(@RequestBody GuessDTO guessDTO) throws GuessWrongSizeException, GameOverException, InvalidPropertiesFormatException, TimesUpException {
        Game g = gameService.getGameById(Long.valueOf(guessDTO.getGameId()));
        gameService.addGuess(g, guessDTO.getGuess());
        return new GameStateDTO(g);
    }
}
