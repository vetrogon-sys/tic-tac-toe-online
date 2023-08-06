package org.example.tic_tac_toe_game.menu;

import org.example.tic_tac_toe_game.util.ConsoleUtil;

import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class GameMenu {

    private static final Scanner SCANNER = new Scanner(System.in);

    public void showMenu(List<MenuItem> menuItems) {

        do {
            ConsoleUtil.clearConsole();
            printMenuItems(menuItems);
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if ((numericValue == -1) || (numericValue < 1 || numericValue > menuItems.size())) {
                System.out.println("Try again");
                continue;
            }
            menuItems.get(numericValue - 1).consumer().accept(1);
            break;
        } while (true);

    }

    private void printMenuItems(List<MenuItem> menuItems) {
        IntStream.range(0, menuItems.size())
              .forEachOrdered(i -> System.out.printf("%d. %s%n", i + 1, menuItems.get(i).name()));
    }
}
