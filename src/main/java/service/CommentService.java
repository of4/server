package service;

import dao.CommentDao;
import model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentDao commentDao;

    @Transactional
    public void create(int postId, String text) {
        commentDao.create(postId, text);
    }

    @Transactional
    public void delete(Comment comment) {
        commentDao.delete(comment);
    }

    @Transactional
    public void update(Comment comment) {
        commentDao.update(comment);
    }

    @Transactional
    public List<Comment> getCommentsThisPost(int postId) {
        return commentDao.getComments(postId);
    }
}
