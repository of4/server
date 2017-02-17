package dao;

import model.Post;
import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public User getUser(int id) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("from User u where u.id = ?");
        query.setParameter(0, id);
        return (User) query.uniqueResult();
    }

    public User getUser(String email, String password) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("from User u where u.email = ? and u.password = ?");
        query.setParameter(0, email);
        query.setParameter(1, password);
        return (User) query.uniqueResult();
    }

    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);//.saveOrUpdate(user);
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    public List<Post> getFavoritesPosts(User user) {
//        return sessionFactory.getCurrentSession().createQuery("FROM Post where ").list();
        return new ArrayList<>();
    }

    public User getById(Long userId) {
        return sessionFactory.getCurrentSession().get(User.class, userId);
    }

    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").list();
    }
}
