package util;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import model.Location;

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

    public static String getCategory(String content) {
        return parser.parse(content).
                getAsJsonObject().
                getAsJsonPrimitive("category").getAsString();
    }

    public static Location getLocation(String content) {
        return new Gson().fromJson(parser.parse(content).
                        getAsJsonObject().getAsJsonObject("location"), Location.class);
    }
}
