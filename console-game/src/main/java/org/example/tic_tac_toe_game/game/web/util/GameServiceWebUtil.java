package org.example.tic_tac_toe_game.game.web.util;

import lombok.SneakyThrows;
import org.codehaus.plexus.util.StringUtils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

public final class GameServiceWebUtil {

    private static final String SERVER_DOMAIN = "localhost:8080";
    private static final String SERVER_ORIGIN = "http://%s/games".formatted(SERVER_DOMAIN);
    public static final String ACCEPT_HEADER = "accept";
    public static final String CONTENT_TYPE_JSON = "application/json";

    private GameServiceWebUtil() {}

    @SneakyThrows
    public static <T> T get(String path, Class<T> responseClass) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create("%s%s".formatted(SERVER_ORIGIN, path)))
              .header(ACCEPT_HEADER, CONTENT_TYPE_JSON)
              .build();

        return client.send(request, new JsonBodyHandler<>(responseClass)).body().get();
    }

    @SneakyThrows
    public static <T> T post(String path, Class<T> responseClass) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create("%s%s".formatted(SERVER_ORIGIN, path)))
              .header(ACCEPT_HEADER, CONTENT_TYPE_JSON)
              .POST(HttpRequest.BodyPublishers.noBody())
              .build();

        return client.send(request, new JsonBodyHandler<>(responseClass)).body().get();
    }

    @SneakyThrows
    public static <T> T put(String path, Class<T> responseClass) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create("%s%s".formatted(SERVER_ORIGIN, path)))
              .header(ACCEPT_HEADER, CONTENT_TYPE_JSON)
              .PUT(HttpRequest.BodyPublishers.noBody())
              .build();

        HttpResponse<Supplier<T>> response = client.send(request, new JsonBodyHandler<>(responseClass));
        if (response.statusCode() != 200) {
            throw new RuntimeException("Wrong request");
        }
        return response.body().get();
    }

    @SneakyThrows
    public static void delete(String path) {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(URI.create("%s%s".formatted(SERVER_ORIGIN, path)))
              .header(ACCEPT_HEADER, CONTENT_TYPE_JSON)
              .DELETE()
              .build();

        client.send(request, new JsonBodyHandler<>(Void.class));
    }

}
