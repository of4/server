package dao;

import model.Favorite;
import model.Post;
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

    public void delete(Favorite favorite) {
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
            posts.add(sessionFactory.getCurrentSession().get(Post.class, favorite.getPostId()));
        }
        return posts;
    }
}
