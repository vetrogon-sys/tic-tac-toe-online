package org.example.tic_tac_toe.server;

import lombok.RequiredArgsConstructor;
import org.example.tic_tac_toe.server.responce.ServerResponse;
import org.example.tic_tac_toe.server.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @PostMapping("/{player}/state")
    public ServerResponse getState(@PathVariable String player, @RequestParam String state) {
        return gameService.generateResponse(player, state);
    }

    @GetMapping("/state")
    public String updateState() {
        return gameService.getState();
    }
}
