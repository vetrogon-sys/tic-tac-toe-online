package org.example.tic_tac_toe_game.menu;

import java.util.function.Consumer;

public record MenuItem(String name, Consumer<Integer> consumer) {
}
