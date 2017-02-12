package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "commentid")
    private int commentId;
//    @Column(name = "postid")
//    private int postId;
//    @Column(name = "userid")
//    private int userId;
    @Column(name = "text")
    private String text;
//    @Column(name = "createtime")
//    private long createTime;
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "postid")
//    private Post post;
//    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "userid")
//    private User user;

    public Comment() {

    }

    public Comment(String text, long createTime, User user) {
        this.text = text;
//        this.createTime = createTime;
//        this.user = user;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public int getPostId() {
//        return postId;
//    }
//
//    public void setPostId(int postId) {
//        this.postId = postId;
//    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

//    public long getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime(long createTime) {
//        this.createTime = createTime;
//    }

//    public Post getPost() {
//        return post;
//    }
//
//    public void setPost(Post post) {
//        this.post = post;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}