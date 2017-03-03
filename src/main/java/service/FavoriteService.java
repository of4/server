package service;

import dao.FavoriteDao;
import model.Favorite;
import model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FavoriteService {

    @Autowired
    FavoriteDao favoriteDao;

    @Transactional
    public void delete(int userId, int postId) {
        favoriteDao.delete(userId, postId);
    }

    @Transactional
    public void update(Favorite favorite) {
        favoriteDao.update(favorite);
    }

    @Transactional
    public void addToFavorite(int userId, int postId) {
        favoriteDao.addToFavorite(userId, postId);
    }

    @Transactional
    public List<Post> getFavorites(int userId) {
        return favoriteDao.getFavorites(userId);
    }

    @Transactional
    public boolean isFavorite(int userId, int postId) {
        return favoriteDao.isFavorite(userId, postId);
    }
}
