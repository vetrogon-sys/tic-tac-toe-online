package org.example.tic_tac_toe_game.game.web.model;

import java.io.Serializable;

public record GameModel(Long id, String name, String state, String hostIp, String secondPlayerIp, String currentPlayer) implements Serializable {
}
