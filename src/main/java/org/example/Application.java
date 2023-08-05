package org.example;

import org.example.tic_tac_toe.game.Game;
import org.example.tic_tac_toe.menu.MenuItem;
import org.example.tic_tac_toe.menu.StartMenu;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        StartMenu startMenu = new StartMenu();
        Game game = new Game();
        startMenu.showMenu(List.of(
            new MenuItem(1, "Create game", game::start),
            new MenuItem(2, "Join Game", game::start),
            new MenuItem(3, "Exit", Application::exit)
        ));
    }

    private static void exit(Void unused) {
        System.exit(1);
    }


}
