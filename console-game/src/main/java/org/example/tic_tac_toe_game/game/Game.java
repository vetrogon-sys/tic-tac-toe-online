package org.example.tic_tac_toe_game.game;

import org.codehaus.plexus.util.StringUtils;
import org.example.tic_tac_toe_game.ConsoleGame;
import org.example.tic_tac_toe_game.game.web.controller.GameController;
import org.example.tic_tac_toe_game.game.web.model.GameResponse;
import org.example.tic_tac_toe_game.util.ConsoleUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
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
        List<GameResponse> games = gameController.getGames();

        IntStream.range(0, games.size())
              .forEachOrdered(i -> System.out.printf("%d. %s%n", i + 1, games.get(i).game().name()));

        do {
            ConsoleUtil.clearConsole();
            System.out.println("Enter game number");
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if ((numericValue == -1) || (numericValue < 1 || numericValue > games.size())) {
                System.out.println("Try again");
                continue;
            }

            GameResponse gameResponse = games.get(numericValue - 1);

            GameResponse game = gameController.joinGame(gameResponse.game().id());

            ConsoleGame consoleGame = new ConsoleGame();
            consoleGame.playAsGuest(game);
            return;
        } while (true);
    }
}
