package dao;

import model.Comment;
import model.User;
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
        Query queryComments = sessionFactory.getCurrentSession().
                createQuery("from Comment c where c.postId = :postId");
        queryComments.setParameter("postId", postId);
        List<Comment> comments = queryComments.list();
        for (Comment comment : comments) {
            comment.setUser(sessionFactory.getCurrentSession().
                    get(User.class, comment.getUserId()));
            comment.setTimeOffset(System.currentTimeMillis() - comment.getCreateTime().getTime());
        }
        return comments;
    }
}
