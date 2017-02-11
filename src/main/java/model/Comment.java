package model;

public class Comment {
    private int commentId;
    private int postId;
    private String text;
    private long timeOffset;
    private User user;

    public Comment(int commentId, int postId, String text, long timeOffset, User user) {
        this.commentId = commentId;
        this.postId = postId;
        this.text = text;
        this.timeOffset = timeOffset;
        this.user = user;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimeOffset() {
        return timeOffset;
    }

    public void setTimeOffset(long timeOffset) {
        this.timeOffset = timeOffset;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}