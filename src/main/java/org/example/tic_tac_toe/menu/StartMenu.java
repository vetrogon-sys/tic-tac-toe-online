package org.example.tic_tac_toe.menu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class StartMenu {

    private static final Scanner SCANNER = new Scanner(System.in);
    private int choise = 0;

    public void showMenu(List<MenuItem> menuItems) {

        do {
            menuItems.forEach(item -> {
                StringBuilder builder = new StringBuilder();
                if (item.id() - 1 == choise) {
                    builder.append(">");
                } else {
                    builder.append(" ");
                }
                builder.append("%d %s".formatted(item.id(), item.name()));
                System.out.println(builder);
            });
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if (numericValue <= menuItems.size() && numericValue > 0) {
                choise = numericValue - 1;
            }
            if (input[0] == 'e') {
                menuItems.get(choise).apply().accept(null);
                break;
            }
        } while (true);
    }

}
