package model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.File;
import java.io.Serializable;

@Entity
@Table(name = "favorite")
public class Favorite {

    @Embeddable
    class PK implements Serializable {
        int postId;
        int userID;
    }

    @EmbeddedId
    private PK pk;

    @Column(name = "post_id")
    private int postId;
    @Column(name = "user_id")
    private int userID;

    public Favorite() {

    }

    public PK getPk() {
        return pk;
    }

    public void setPk(int userId, int postId) {
        PK pk = new PK();
        this.userID = pk.userID = userId;
        this.postId = pk.postId = postId;
        this.pk = pk;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}
