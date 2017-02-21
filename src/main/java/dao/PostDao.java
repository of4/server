package dao;

import model.Location;
import model.Post;
import org.hibernate.Query;
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
        return sessionFactory.getCurrentSession().createQuery("FROM Post").list();
    }

    public List<Post> getNearPosts(Location location) {
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        double radius = 1.4;
        Query query = sessionFactory.getCurrentSession().
                createQuery("from Post p where (p.location.latitude - ?) * (p.location.latitude - ?)  + (p.location.longitude - ?) * (p.location.longitude - ?) < ?");
        query.setParameter(0, latitude);
        query.setParameter(1, latitude);
        query.setParameter(2, longitude);
        query.setParameter(3, longitude);
        query.setParameter(4, radius * radius);

//        double distance = Math.sqrt(Math.pow(postLocation.getLatitude() - currentLocation.getLatitude(), 2) +
//                Math.pow(postLocation.getLongitude() - currentLocation.getLongitude(), 2));//locationA.distanceTo(locationB);
//        return distance / 1000;
//
//        double radius = 5;
//        Query query = sessionFactory.getCurrentSession().
//                createQuery("from Post p");
//        List<Post> postList = query.list();
//        List<Post> postInRadius = new ArrayList<>();
//
//        for (Post currentPost : postList) {
//            Location postLocation = currentPost.lo
//        }
//        query.setParameter(0, post.getLocationId());
//        return query.list();
        return query.list();
    }
}
