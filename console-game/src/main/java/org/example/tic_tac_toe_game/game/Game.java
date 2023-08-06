package org.example.tic_tac_toe_game.game;

import org.example.tic_tac_toe_game.game.web.controller.GameController;
import org.example.tic_tac_toe_game.game.web.model.GameResponse;
import org.example.tic_tac_toe_game.util.ConsoleUtil;

import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.IntStream;

public class Game {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final GameController gameController = new GameController();

    public static void create(Integer code) {
        ConsoleUtil.clearConsole();
        System.out.println("Enter game name: ");
        String gameName = SCANNER.next();

        GameResponse game = gameController.createGame(gameName);
        ConsoleGame consoleGame = new ConsoleGame();
        consoleGame.playAsHost(game);

    }

    public static void join(Integer code) {

        do {
            ConsoleUtil.clearConsole();

            Timer myTimer;
            myTimer = new Timer();

            String[] threadInput = new String[]{""};

            final GameResponse[][] gameResponses = {{}};
            myTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        ConsoleUtil.clearConsole();
                        List<GameResponse> games = gameController.getGames();
                        gameResponses[0] = games.toArray(GameResponse[]::new);
                        System.out.println("Available games:");
                        IntStream.range(0, games.size())
                              .forEachOrdered(i -> System.out.printf("%d. %s [host:%s]%n", i + 1, games.get(i).game().name(), games.get(i).game().hostIp()));
                    } catch (IndexOutOfBoundsException e) {
                        myTimer.purge();
                    }
                }
            }, 0, 5000);


            System.out.println("Enter game number");
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if ((numericValue == -1) || (numericValue < 1 || numericValue > gameResponses[0].length)) {
                System.out.println("Try again");
                continue;
            }

            myTimer.cancel();
            GameResponse gameResponse = gameResponses[0][numericValue - 1];

            GameResponse game = gameController.joinGame(gameResponse.game().id());

            ConsoleGame consoleGame = new ConsoleGame();
            consoleGame.playAsGuest(game);
            return;
        } while (true);
    }
}
