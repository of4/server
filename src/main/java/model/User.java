package model;

public class User {
    private int id;
    private String name;
    private String email;
    private String token;
    private String avatarUrl;

    public User() {

    }

    public User(int id, String name, String email, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
