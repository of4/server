package service;

import dao.FavoriteDAO;
import model.Favorite;
import model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FavoriteService {

    @Autowired
    FavoriteDAO favoriteDAO;

    @Transactional
    public void delete(int userId, int postId) {
        favoriteDAO.delete(userId, postId);
    }

    @Transactional
    public void update(Favorite favorite) {
        favoriteDAO.update(favorite);
    }

    @Transactional
    public void addToFavorite(int userId, int postId) {
        favoriteDAO.addToFavorite(userId, postId);
    }

    @Transactional
    public List<Post> getFavorites(int userId) {
        return favoriteDAO.getFavorites(userId);
    }

    @Transactional
    public boolean isFavorite(int userId, int postId) {
        return favoriteDAO.isFavorite(userId, postId);
    }
}
