package org.example.tic_tac_toe.server.service;

import org.example.tic_tac_toe.server.responce.ServerResponse;

public interface GameService {

    ServerResponse generateResponse(String player, String state);

    String getState();
}
