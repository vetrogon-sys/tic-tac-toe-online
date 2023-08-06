package org.example.tic_tac_toe_game.game.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tic_tac_toe_game.game.Game;
import org.example.tic_tac_toe_game.game.web.model.GameModel;
import org.example.tic_tac_toe_game.game.web.model.GameResponse;
import org.example.tic_tac_toe_game.game.web.util.GameServiceWebUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class GameController {

    public GameResponse createGame(String name) {
        return GameServiceWebUtil.post("/%s".formatted(name), GameResponse.class);
    }

    public GameResponse joinGame(Long gameId) {
        return GameServiceWebUtil.put("/%d".formatted(gameId), GameResponse.class);
    }

    public List<GameResponse> getGames() {
        List list = GameServiceWebUtil.get("", List.class);
        ObjectMapper objectMapper = new ObjectMapper();
        return list.stream()
              .map(entry -> objectMapper.convertValue(entry, GameResponse.class))
              .toList();
    }

    public GameResponse getState(Long gameId) {
        return GameServiceWebUtil.get("/%d/state".formatted(gameId), GameResponse.class);
    }

    public GameResponse updateState(Long gameId, String state) {
        return GameServiceWebUtil.put("/%d/state/%s".formatted(gameId, state), GameResponse.class);
    }

    public void deleteGame(Long gameId) {
        GameServiceWebUtil.delete("/%d".formatted(gameId));
    }

    public void deleteAll() {
        GameServiceWebUtil.delete("");
    }
}
