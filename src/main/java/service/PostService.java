package service;

import dao.PostDao;
import model.Location;
import model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PostService {

    @Autowired
    private PostDao postDao;

    @Transactional
    public void create(Post post) {
        postDao.create(post);
    }

    @Transactional
    public void delete(Post post) {
        postDao.delete(post);
    }

    @Transactional
    public void update(Post post) {
        postDao.update(post);
    }

    @Transactional
    public List<Post> getNearPosts(Location location) {
        return postDao.getPosts(location);
    }
}
