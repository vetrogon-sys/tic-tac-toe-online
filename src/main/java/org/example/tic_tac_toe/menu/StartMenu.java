package org.example.tic_tac_toe.menu;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class StartMenu {

    private static final String USER_CHAR = ">";
    private static final String EMPTY_CHAR = " ";
    public static final Scanner SCANNER = new Scanner(System.in);
    public static final int WIDTH = 880;
    public static final int HEIGHT = 480;

    private int userPosition = 1;

    public void showMenu(List<MenuItem> menuItems) throws InterruptedException {

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);

        frame.add(new Menu(frame, menuItems));

        frame.setVisible(true);
    }

}
