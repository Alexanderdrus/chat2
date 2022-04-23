package ru.gb.gbchat2.server;
import java.util.HashMap;
import java.util.Map;

public class AuthTimer {
    private static AuthTimer authTimer = new AuthTimer();
    private static Map<ClientHandler, Long> nonAuthorizedSockets = new HashMap<>();
    private final static long TIME_LIMIT = 120000;

    private AuthTimer() {
        new Thread(() -> {
            while (true) {
                nonAuthorizedSockets.forEach(((client, aLong) -> {
                    if ((System.currentTimeMillis() - aLong) > TIME_LIMIT) {
                        client.sendMessage("Время на авторизацию истекло.\n Соединение разорвано.");
                        client.closeSocket();
                    }
                }));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static void set(ClientHandler client) {
        nonAuthorizedSockets.put(client, System.currentTimeMillis());
    }

    public static void unset(ClientHandler client) {
        nonAuthorizedSockets.remove(client);
    }
}