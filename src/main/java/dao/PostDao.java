package dao;

import model.Comment;
import model.Favorite;
import model.Location;
import model.Post;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
        try (Session s = sessionFactory.openSession()) {
            return s.createCriteria(Post.class).list();
        }

//        return sessionFactory.getCurrentSession().createQuery("FROM Post").list();
    }

    public void addToFavorite(int userId, int postId) {
        Favorite favorite = new Favorite();
        favorite.setPk(userId, postId);
        sessionFactory.getCurrentSession().save(favorite);
    }

    public List<Post> getFavorites(int userId) {
        Query queryFavorites = sessionFactory.getCurrentSession().
                createQuery("from Favorite f where f.userID = ?");
        queryFavorites.setParameter(0, userId);
        List<Favorite> favorites = queryFavorites.list();
        List<Post> posts = new ArrayList<>();
        for (Favorite favorite : favorites) {
            posts.add(sessionFactory.getCurrentSession().get(Post.class, favorite.getPostId()));
        }
        return posts;
    }

    public List<Comment> getComments(int postId) {
        /*
        //        Query query = sessionFactory.getCurrentSession().
        //          createQuery("from Comment c where c.postId = ?");

        Query commentCuery = sessionFactory.getCurrentSession().
                createQuery("FROM Comment c where c.postId = ?");
        commentCuery.setParameter(0, postId);
        List<Comment> comments = commentCuery.list();
        for (Comment comment : comments) {
            Query queryUsers = sessionFactory.getCurrentSession().
                    createQuery("FROM User u where u.id = ?");
            queryUsers.setParameter(0, comment.getUserId());
            comment.setUser((User) queryUsers.list().get(0));
        }
        return comments;
         */
        Query queryComments = sessionFactory.getCurrentSession().
                createQuery("from Comment c where c.postId = ?");
        queryComments.setParameter(0, postId);
        List<Comment> comments = queryComments.list();
        for (Comment comment : comments) {
            Query queryUsers = sessionFactory.getCurrentSession().
                    createQuery("FROM User u where u.id = ?");
            queryUsers.setParameter(0, comment.getUserId());
            comment.setUser((User) queryUsers.list().get(0));
        }
        return comments;
    }

    public List<Post> getNearPosts(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double radius = 0.06;
        Query queryLocations = sessionFactory.getCurrentSession().
                createQuery("from Location l where ((l.latitude - ?) * (l.latitude - ?)  + " +
                        "(l.longitude - ?) * (l.longitude - ?)) < ?");
        queryLocations.setParameter(0, latitude);
        queryLocations.setParameter(1, latitude);
        queryLocations.setParameter(2, longitude);
        queryLocations.setParameter(3, longitude);
        queryLocations.setParameter(4, radius * radius);
        List<Location> nearLocations = queryLocations.list();
        List<Post> posts = new ArrayList<>();

        for (Location nearLocation : nearLocations) {
            Query queryPost = sessionFactory.getCurrentSession().
                    createQuery("from Post p where p.locationId = ?");
            queryPost.setParameter(0, nearLocation.getId());
            Post post = (Post) queryPost.list().get(0);
            Query queryUser = sessionFactory.getCurrentSession().
                    createQuery("from User u where u.id = ?");
            queryUser.setParameter(0, post.getUserId());
            post.setUser((User) queryUser.list().get(0));
            post.setLocation(nearLocation);
            posts.add(post);
        }

        return posts;
    }
}
