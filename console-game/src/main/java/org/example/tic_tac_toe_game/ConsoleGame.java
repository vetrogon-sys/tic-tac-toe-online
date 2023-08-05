package org.example.tic_tac_toe_game;

import lombok.SneakyThrows;
import org.codehaus.plexus.util.StringUtils;
import org.example.tic_tac_toe_game.game.Game;
import org.example.tic_tac_toe_game.game.web.controller.GameController;
import org.example.tic_tac_toe_game.game.web.model.GameModel;
import org.example.tic_tac_toe_game.game.web.model.GameResponse;
import org.example.tic_tac_toe_game.util.ConsoleUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ConsoleGame {

    private final Scanner SCANNER = new Scanner(System.in);
    private final GameController gameController;

    public ConsoleGame() {
        gameController = new GameController();
    }

    public void playAsHost(GameResponse gameResponse) {

        LocalDateTime startTime = LocalDateTime.now();
        do {
            ConsoleUtil.clearConsole();
            try {
                System.out.println("Whaiting for another player to start game");
                System.out.println(Duration.between(startTime, LocalDateTime.now()).toString()
                      .substring(2)
                      .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                      .toLowerCase());

                GameResponse state = gameController.getState(gameResponse.game().id());
                if (StringUtils.isNotBlank(state.game().secondPlayerIp())) {
                    play(state.game(), "x");
                    return;
                }

                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } while (true);

    }

    public void playAsGuest(GameResponse gameResponse) {
        play(gameResponse.game(), "0");
    }

    private void play(GameModel game, String player) {

        final String[] oldState = {game.state()};
        var ref = new Object() {
            boolean inGame = true;
        };
        String currentPlayer;

        new Thread(() -> {
            while (ref.inGame) {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                oldState[0] = gameController.getState(game.id()).game().state();
            }
        }).start();

        do {
            ConsoleUtil.clearConsole();
            GameResponse currentState = gameController.getState(game.id());
            oldState[0] = currentState.game().state();

            if (!lookLikeBoard(oldState[0])) {
                ref.inGame = false;
            }

            printBoard(oldState[0]);
            currentPlayer = currentState.game().currentPlayer();
            if (player.equals(currentPlayer)) {
                System.out.println("Enter you move");
            } else {
                System.out.println("Wait opponent");
                continue;
            }
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if (numericValue == -1 || !isValidMove(numericValue, oldState[0])) {
                System.out.println("Try again");
                continue;
            }

            String state = getNewState(numericValue, currentPlayer, oldState[0]);

            gameController.updateState(game.id(), state);
        } while (ref.inGame);
        System.out.println(oldState[0]);

        gameController.deleteGame(game.id());
    }

    private boolean lookLikeBoard(String state) {
        return state.matches("[x,0,-]+"); //todo not working
    }

    private String getNewState(int numericValue, String player, String oldState) {
        char[] stateAsArray = oldState.toCharArray();
        stateAsArray[numericValue - 1] = player.charAt(0);
        return String.valueOf(stateAsArray);
    }

    private boolean isValidMove(int numericValue, String state) {
        return state.charAt(numericValue - 1) == '-';
    }

    private void printBoard(String state) {
        char[] stateAsChar = state.toCharArray();
        for (int i = 0; i < 9; i += 3) {
            System.out.println("%s|%s|%s".formatted(stateAsChar[i], stateAsChar[i + 1], stateAsChar[i + 2]));
        }
    }

}
