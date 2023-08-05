package org.example.tic_tac_toe_game;

import org.example.tic_tac_toe_game.game.Game;
import org.example.tic_tac_toe_game.menu.GameMenu;
import org.example.tic_tac_toe_game.menu.MenuItem;

import java.util.List;

public class GameApplication {

    private static final List<MenuItem> GAME_MENU_ITEMS = List.of(
          new MenuItem("Create game", Game::create),
          new MenuItem("Join game", Game::join),
          new MenuItem("Exit", GameApplication::exit)
    );

    public static void main(String[] args) {
        GameMenu gameMenu = new GameMenu();
        gameMenu.showMenu(GAME_MENU_ITEMS);
    }

    public static void game(Integer i) {

    }

    public static void exit(Integer code) {
        System.exit(code);
    }

}
