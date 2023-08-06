package org.example.tic_tac_toe_game.game.web.util;

import lombok.SneakyThrows;
import org.codehaus.plexus.util.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.function.Supplier;

public final class GameServiceWebUtil {

    private static final String SERVER_ORIGIN = getDomain();
    public static final String ACCEPT_HEADER = "accept";
    public static final String CONTENT_TYPE_JSON = "application/json";

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

        return client.send(request, new JsonBodyHandler<>(responseClass)).body().get();
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

    private static String getDomain() {
        String origin = "http://%s/games".formatted("localhost:0000");
        try (InputStream input = GameServiceWebUtil.class.getClassLoader().getResourceAsStream("server-config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            origin = "http://%s/games".formatted(prop.getProperty("server.domain"));
        } catch (IOException ignore) {
        }
        return origin;
    }

}
