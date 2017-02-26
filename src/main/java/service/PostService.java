package service;

import dao.PostDao;
import model.Location;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    @Autowired
    LocationService locationService;

    @Transactional
    public void create(Post post, User user) {
        locationService.create(post.getLocation());
        post.setLocationId(post.getLocation().getId());
        post.setUserId(user.getId());
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
    public void addToFavorite(int userId, int postId) {
        postDao.addToFavorite(userId, postId);
    }

    @Transactional
    public List<Post> getFavorites(int userId) {
        return postDao.getFavorites(userId);
    }

    @Transactional
    public List<Post> getNearPosts(Location location, String category) {
        if (category.equals("ALL")) {
            return postDao.getAllNearPosts(location);
        } else {
            return postDao.getNearPosts(location, category);
        }
    }
}
