package dao;

import model.Favorite;
import model.Location;
import model.Post;
import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FavoriteDAO {

    @Autowired
    SessionFactory sessionFactory;

    public void create(Favorite favorite) {
        sessionFactory.getCurrentSession().save(favorite);
    }

    public void update(Favorite favorite) {
        sessionFactory.getCurrentSession().update(favorite);
    }

    public void delete(int userId, int postId) {
        Favorite favorite = new Favorite();
        favorite.setPk(userId, postId);
        sessionFactory.getCurrentSession().delete(favorite);
    }

    public void addToFavorite(int userId, int postId) {
        Favorite favorite = new Favorite();
        favorite.setPk(userId, postId);
        sessionFactory.getCurrentSession().save(favorite);
    }

    public List<Post> getFavorites(int userId) {
        Query queryFavorites = sessionFactory.getCurrentSession().
                createQuery("from Favorite f where f.userID = :userId");
        queryFavorites.setParameter("userId", userId);
        List<Favorite> favorites = queryFavorites.list();
        List<Post> posts = new ArrayList<>();
        for (Favorite favorite : favorites) {
            Post post = sessionFactory.getCurrentSession().get(Post.class, favorite.getPostId());
            post.setUser(sessionFactory.getCurrentSession().get(User.class, post.getUserId()));
            post.setLocation(sessionFactory.getCurrentSession().get(Location.class, post.getLocationId()));
            posts.add(post);
        }
        return posts;
    }

    public boolean isFavorite(int userId, int postId) {
        Favorite favorite = new Favorite();
        favorite.setPk(userId, postId);
        return sessionFactory.getCurrentSession().get(Favorite.class, favorite.getPk()) != null;
    }
}
