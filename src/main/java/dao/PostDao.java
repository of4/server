package dao;

import model.Favorite;
import model.Location;
import model.Post;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PostDao {

    @Autowired
    SessionFactory sessionFactory;

    public void create(Post post) {
        sessionFactory.getCurrentSession().save(post);
    }

    public void update(Post post) {
        sessionFactory.getCurrentSession().update(post);
    }

    public void delete(Post post) {
        sessionFactory.getCurrentSession().delete(post);
    }

    public List<Post> getAllPosts() {
        return sessionFactory.getCurrentSession().createCriteria(Post.class).list();
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

    public List<Post> getAllNearPosts(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double radius = 0.06;
        Query queryLocations = sessionFactory.getCurrentSession().
                createQuery("from Location l where ((l.latitude - :latitude) * (l.latitude - latitude)  + " +
                        "(l.longitude - :longitude) * (l.longitude - :longitude)) < :radius");
        queryLocations.setParameter("latitude", latitude);
        queryLocations.setParameter("longitude", longitude);
        queryLocations.setParameter("radius", radius * radius);
        List<Location> nearLocations = queryLocations.list();
        List<Post> posts = new ArrayList<>();
        for (Location nearLocation : nearLocations) {
            Post post = (Post) sessionFactory.getCurrentSession().
                    createCriteria(Post.class).
                    add(Restrictions.eq("locationId", nearLocation.getId())).
                    uniqueResult();
            post.setUser(sessionFactory.getCurrentSession().
                    get(User.class, post.getUserId()));
            post.setLocation(nearLocation);
            posts.add(post);
        }
        return sift(posts, location, radius / 2);
    }


    public List<Post> getNearPosts(Location location, String category) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double radius = 0.06;
        Query queryLocations = sessionFactory.getCurrentSession().
                createQuery("from Location l where ((l.latitude - :latitude) * (l.latitude - latitude)  + " +
                        "(l.longitude - :longitude) * (l.longitude - :longitude)) < :radius");
        queryLocations.setParameter("latitude", latitude);
        queryLocations.setParameter("longitude", longitude);
        queryLocations.setParameter("radius", radius * radius);
        List<Location> nearLocations = queryLocations.list();
        List<Post> posts = new ArrayList<>();
        for (Location nearLocation : nearLocations) {
            Post post = (Post) sessionFactory.getCurrentSession().
                    createCriteria(Post.class).
                    add(Restrictions.and(Restrictions.eq("locationId", nearLocation.getId()),
                    Restrictions.eq("category", category))).
                    uniqueResult();
            post.setUser(sessionFactory.getCurrentSession().
                    get(User.class, post.getUserId()));
            post.setLocation(nearLocation);
            posts.add(post);
        }
        return sift(posts, location, radius / 2);
    }

    private List<Post> sift(List<Post> posts, Location location, double radius) {
        List<Post> siftedPosts = new ArrayList<>();
        for (Post post : posts) {
            if (post.getUser().isAdvertiser() ||
                    Math.pow(post.getLocation().getLongitude() - location.getLongitude(), 2)
                            + Math.pow(post.getLocation().getLatitude() - location.getLatitude(), 2) < radius) {
                post.setTimeOffset(System.currentTimeMillis() - post.getCreateTime().getTime());
                siftedPosts.add(post);
            }
        }
        return siftedPosts;
    }
}
