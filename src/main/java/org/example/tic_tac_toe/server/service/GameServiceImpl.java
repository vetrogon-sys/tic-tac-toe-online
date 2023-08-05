package org.example.tic_tac_toe.server.service;

import org.example.tic_tac_toe.server.responce.ServerResponse;
import org.example.tic_tac_toe.server.responce.Winner;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class GameServiceImpl implements GameService {

    private String currentState = "---------";

    private final String[] winningCombination = new String[]{
          "111000000",
          "000111000",
          "000000111",
          "100010001",
          "010010010",
          "001010100",
    };

    @Override
    public ServerResponse generateResponse(String player, String state) {
        String opponent = "x".equals(player) ? "0" : "x";
        String validState = state
              .replaceAll(player, "1")
              .replaceAll("[%s,-]".formatted(opponent), "0");
        boolean isWin = Arrays.stream(winningCombination)
              .filter(comd -> comd.equals(validState))
              .count() == 1;
        Winner winner = Winner.NONE;
        String updatedState = state;
        if (isWin) {
            if ("x".equals(player)) {
                winner = Winner.CROSS_PLAYER;
                updatedState = "Cross player won";
            } else {
                winner = Winner.ZERO_PLAYER;
                updatedState = "Zero player won";
            }
        } else if (!state.contains("-")) {
            winner = Winner.DRAW;
            updatedState = "Game draw";
        }

        currentState = updatedState;
        return new ServerResponse(winner, updatedState);
    }

    @Override
    public String getState() {
        return currentState;
    }
}
