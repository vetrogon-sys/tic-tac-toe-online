package org.example.tic_tac_toe_server.service;

import lombok.RequiredArgsConstructor;
import org.example.tic_tac_toe_server.dto.GameResponse;
import org.example.tic_tac_toe_server.model.Game;
import org.example.tic_tac_toe_server.repository.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    public static final String START_GAME_STATE = "---------";
    private final List<String> winningCombination = List.of(
          //vertical
          "111000000",
          "000111000",
          "000000111",
          //horizontal
          "100100100",
          "010010010",
          "001001001",
          //diagonal
          "100010001",
          "001010100"
    );

    private final GameRepository gameRepository;

    @Override
    public GameResponse createGame(String name, String ipAddress) {
        return new GameResponse(gameRepository.save(Game.builder()
              .name(name)
              .state(START_GAME_STATE)
              .hostIp(ipAddress)
              .build()
        ));
    }

    @Override
    public GameResponse joinGame(Long gameId, String ipAddress) {
        Game game = getGameById(gameId);
        game.setSecondPlayerIp(ipAddress);
        return new GameResponse(gameRepository.save(game));
    }

    @Override
    public List<GameResponse> getAvailableGames() {
        return gameRepository.findAll().stream()
              .map(GameResponse::new)
              .toList();
    }

    @Override
    public GameResponse updateGameState(Long gameId, String state, String ipAddress) {
        Game game = getGameById(gameId);
        String player = ipAddress.equals(game.getHostIp()) ? "x" : "0";
        String opponent = "x".equals(player) ? "0" : "x";
        String validationState = state
              .replaceAll(player, "1")
              .replaceAll("[%s,-]".formatted(opponent), "0");

        boolean isWinner = winningCombination.stream()
              .filter(combination -> combination.equals(validationState))
              .count() == 1;
        String updatedState;
        if (isWinner) {
            updatedState = "'%s' player win".formatted(player);
        } else if (!state.contains("-")) {
            updatedState = "Game draw";
        } else {
            updatedState = state;
        }

        game.setState(updatedState);
        return new GameResponse(gameRepository.save(game));
    }

    @Override
    public GameResponse getGameState(Long gameId) {
        return new GameResponse(getGameById(gameId));
    }

    @Override
    public void removeGame(Long id) {
        gameRepository.deleteById(id);
    }

    private Game getGameById(Long gameId) {
        return gameRepository.findById(gameId)
              .orElseThrow(() -> new RuntimeException("Unavaliable id %d".formatted(gameId)));
    }
}
