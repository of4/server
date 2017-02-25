package util;

import com.google.gson.JsonParser;

public class Parser {

    private static JsonParser parser = new JsonParser();

    public static String getToken(String content) {
        return parser.parse(content).
                getAsJsonObject().
                getAsJsonPrimitive("token").getAsString();
    }

    public static int getPostId(String content) {
        return Integer.parseInt(parser.parse(content).
                getAsJsonObject().
                getAsJsonPrimitive("postId").getAsString());
    }

    public static String getText(String content) {
        return parser.parse(content).
                getAsJsonObject().
                getAsJsonPrimitive("text").getAsString();
    }
}
