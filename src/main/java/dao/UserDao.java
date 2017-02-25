package dao;

import model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao {

    @Autowired
    SessionFactory sessionFactory;

    public User getUser(String email, String password) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("from User u where u.email = :email and u.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        return (User) query.uniqueResult();
    }

    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public void update(User user) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("update User u set  " +
                        "u.advertiser = :adv, " +
                        "u.email = :email, " +
                        "u.name = :name  " +
                        "where u.id = :userId");
        query.setParameter("adv", user.isAdvertiser());
        query.setParameter("email", user.getEmail());
        query.setParameter("name", user.getName());
        query.setParameter("userId", user.getId());
        query.executeUpdate();
        //sessionFactory.getCurrentSession().update(user);
    }

    public void updateToken(User user) {
        Query query = sessionFactory.getCurrentSession().
                createQuery("update User u set  " +
                        "u.token = :token where u.id = :userId");
        query.setParameter("token", user.getToken());
        query.setParameter("userId", user.getId());
        query.executeUpdate();
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

    public User getUserByToken(String token) {
        return (User) sessionFactory.getCurrentSession().createCriteria(User.class).
                add(Restrictions.eq("token", token)).
                uniqueResult();
    }
}
