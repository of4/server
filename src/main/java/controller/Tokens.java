package controller;

import model.User;

import java.util.HashMap;
import java.util.Map;

public class Tokens {

    private static Map<String, User> tokens = new HashMap<>();

    public static User getUserByToken(String token) {
        return tokens.get(token);
    }

    public static void setUserByToken(String token, User user) {
        Tokens.tokens.put(token, user);
    }
}
