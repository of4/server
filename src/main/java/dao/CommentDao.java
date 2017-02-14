package dao;

import javafx.geometry.Pos;
import model.Comment;
import model.Location;
import model.Post;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDao {
    @Autowired
    SessionFactory sessionFactory;

    public void create(int postId, String text) {
        Comment comment = new Comment(text);
        comment.setPostId(postId);
        sessionFactory.getCurrentSession().save(comment);
    }

    public void update(Comment comment) {
        sessionFactory.getCurrentSession().update(comment);
    }

    public void delete(Comment comment) {
        sessionFactory.getCurrentSession().delete(comment);
    }

    public List<Comment> getComments(int postId) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("from Comment c where c.postId = ?");
        query.setParameter(0, postId);
        return query.list();
    }
}
