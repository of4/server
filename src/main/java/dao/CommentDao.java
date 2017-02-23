package dao;

import model.Comment;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDao {

    @Autowired
    SessionFactory sessionFactory;

    public void create(int postId, int userId, String text) {
        Comment comment = new Comment();
        comment.setPostId(postId);
        comment.setUserId(userId);
        comment.setText(text);
        sessionFactory.getCurrentSession().save(comment);
    }

    public void update(Comment comment) {
        sessionFactory.getCurrentSession().update(comment);
    }

    public void delete(Comment comment) {
        sessionFactory.getCurrentSession().delete(comment);
    }

    public List<Comment> getComments(int postId) {
        Query query = sessionFactory.getCurrentSession().createQuery("FROM Comment c where c.postId = ?");
        query.setParameter(0, postId);
        return query.list();
    }
}
