package dao;

import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

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
        Query query = sessionFactory.getCurrentSession().
                createQuery("update User u set  u.advertiser = :adv, " +
                        "u.email = :email, " +
                        "u.name = :name  " +
                        "where u.id = :userId");
        query.setParameter("adv", user.isAdvertiser());
        query.setParameter("email", user.getEmail());
        query.setParameter("name", user.getName());
        query.setParameter("userId", user.getId());
        //sessionFactory.getCurrentSession().update(user);
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    public User getById(Long userId) {
        return sessionFactory.getCurrentSession().get(User.class, userId);
    }

    public List<User> getAll() {
        return sessionFactory.getCurrentSession().createQuery("FROM User").list();
    }
}
