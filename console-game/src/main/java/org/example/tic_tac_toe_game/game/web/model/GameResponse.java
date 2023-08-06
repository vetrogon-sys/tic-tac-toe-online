package org.example.tic_tac_toe_game.game.web.model;

import java.io.Serializable;

public record GameResponse(GameModel game) implements Serializable {
}
