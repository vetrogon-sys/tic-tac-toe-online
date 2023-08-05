package org.example.tic_tac_toe.game;

import java.util.Scanner;

public class Game {

    private final Scanner SCANNER = new Scanner(System.in);

    public void start(Void i) {
        System.out.print("Enter game name: ");
        String gameName = SCANNER.next();
    }

}
