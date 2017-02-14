package dao;

import model.Location;
import model.Post;
import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public List<Post> getPosts(Location location) {
        double radius = 5;
        Query query = sessionFactory.getCurrentSession().
                createQuery("from Post p where p.location.latitude < ?");
        query.setParameter(0, radius);
        return query.list();
    }
}
