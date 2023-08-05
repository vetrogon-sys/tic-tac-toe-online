package org.example.tic_tac_toe_game.util;

import lombok.SneakyThrows;

public final class ConsoleUtil {

    private ConsoleUtil() {
    }

    @SneakyThrows
    public static void clearConsole() {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

//        System.out.print("\033[H\033[2J");
//        System.out.flush();

//        Runtime.getRuntime().exec("cls");
    }

}
