package org.example.tic_tac_toe_server.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.tic_tac_toe_server.dto.GameResponse;
import org.example.tic_tac_toe_server.rxception.UnexpectedStateChangeException;
import org.example.tic_tac_toe_server.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
public class GameController {

    public final GameService gameService;

    @PostMapping("/{gameName}")
    public GameResponse createGame(@PathVariable String gameName, HttpServletRequest request) {
        String ipAddress = getIpAddress(request);
        return gameService.createGame(gameName, ipAddress);
    }

    @PutMapping("/{gameId}")
    public GameResponse joinGame(@PathVariable Long gameId, HttpServletRequest request) {
        String ipAddress = getIpAddress(request);
        return gameService.joinGame(gameId, ipAddress);
    }

    @GetMapping
    public List<GameResponse> getGames() {
        return gameService.getAvailableGames();
    }

    @GetMapping("/{gameId}/state")
    public GameResponse getState(@PathVariable Long gameId) {
        return gameService.getGameState(gameId);
    }

    @PutMapping("/{gameId}/state/{state}")
    public GameResponse updateState(@PathVariable Long gameId, @PathVariable String state, HttpServletRequest request) {
        String ipAddress = getIpAddress(request);
        return gameService.updateGameState(gameId, state, ipAddress);
    }

    @DeleteMapping("/{gameId}")
    public void deleteGame(@PathVariable Long gameId) {
        gameService.removeGame(gameId);
    }

    @DeleteMapping()
    public void deleteAll(HttpServletRequest request) {
        String ipAddress = getIpAddress(request);
        gameService.removeAllByHost(ipAddress);
    }

    @ExceptionHandler({UnexpectedStateChangeException.class })
    public ResponseEntity<String> handleException(UnexpectedStateChangeException exception) {
        return ResponseEntity
              .status(401)
              .body(exception.getMessage());
    }

    private static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }


}
