package service;

import dao.FavoriteDAO;
import dao.PostDao;
import model.Favorite;
import model.Location;
import model.Post;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class FavoriteService {

    @Autowired
    FavoriteDAO favoriteDAO;

    @Transactional
    public void delete(Favorite favorite) {
        favoriteDAO.delete(favorite);
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
}
