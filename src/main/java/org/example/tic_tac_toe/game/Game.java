package org.example.tic_tac_toe.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.tic_tac_toe.server.responce.ServerResponse;
import org.example.tic_tac_toe.server.responce.Winner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Game {

    private final Scanner SCANNER = new Scanner(System.in);

    private String gameState = "---------";

    private Winner winner = Winner.NONE;

    private String player = "x";
    public void start(Void i) {

        System.out.print("Enter game name: ");
        String gameName = SCANNER.next();

        do {
            updateState();
            drawDesk(gameState);
            char[] input = SCANNER.next().toCharArray();
            int numericValue = Character.getNumericValue(input[0]);
            if (numericValue > 0 && numericValue <= 9) {
                char[] gameStateAsArray = gameState.toCharArray();
                if (gameStateAsArray[numericValue-1] == '-') {
                    gameStateAsArray[numericValue-1] = player.charAt(0);
                    sendState(String.valueOf(gameStateAsArray));
                }
            }
        } while (winner == Winner.NONE);



    }

    private void drawDesk(String gameState) {
        char[] charArray = gameState.toCharArray();
        for (int i = 0; i < charArray.length; i+=3) {
            System.out.println("%s|%s|%s".formatted(charArray[i], charArray[i+1], charArray[i+2]));
        }
    }

    @SneakyThrows
    private void updateState() {
        URL url = new URL("http://localhost:8080/game/%s/state");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        InputStream inputStream = con.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        ServerResponse response = mapper.readValue(inputStream, ServerResponse.class);

        gameState = response.gameState();
        winner = response.winner();
    }

    private void sendState(String gameState) {
        //send state to server
        this.gameState = gameState;
    }

}
