package org.example.tic_tac_toe_server.service;

import org.example.tic_tac_toe_server.dto.GameResponse;

import java.util.List;

public interface GameService {

    GameResponse createGame(String name, String ipAddress);

    GameResponse joinGame(Long gameId, String ipAddress);

    List<GameResponse> getAvailableGames();

    GameResponse updateGameState(Long gameId, String state, String ipAddress);

    GameResponse getGameState(Long gameId);

    void removeGame(Long id);

}
