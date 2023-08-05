package org.example.tic_tac_toe.menu;

import java.util.function.Consumer;
import java.util.function.Function;

public record MenuItem(int id, String name, Consumer<Void> apply) {
}
