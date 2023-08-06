package org.example.tic_tac_toe_server.rxception;

public class UnexpectedStateChangeException extends RuntimeException {

    public UnexpectedStateChangeException(String message) {
        super(message);
    }
}
