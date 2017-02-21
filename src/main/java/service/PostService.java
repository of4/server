package service;

import dao.PostDao;
import model.Location;
import model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

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
    public List<Post> getAllPosts() {
        return postDao.getAllPosts();
    }

    @Transactional
    public List<Post> getNearPosts(Location location) {
        return postDao.getNearPosts(location);
//        return new ArrayList<>();
    }
}
