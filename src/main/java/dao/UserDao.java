package dao;

import model.Post;
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

    public Integer createUser(User user) {
        Integer r = (Integer) sessionFactory.getCurrentSession().save(user);
        return r;
    }

    public void deleteUser(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }

    public User getUserByName(String name) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.name = ?");
        query.setParameter(0, name);
        return (User) query.uniqueResult();
    }

    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public void update(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    public User getById(Long userId) {
        return (User) sessionFactory.getCurrentSession().get(User.class, userId);
    }

//    public List<Post> getAllPosts() {
//        return sessionFactory.getCurrentSession().createQuery("FROM Post").list();
//    }

    public List<User> getAll() {
        //noinspection JpaQlInspection
        return sessionFactory.getCurrentSession().createQuery("FROM User order by name").list();
    }

    public User getByLogin(String name) {
        //noinspection JpaQlInspection
        Query query = sessionFactory.getCurrentSession().createQuery("from User u where u.name = ?");
        query.setString(0, name);
        return (User) query.list().get(0);
    }

    public void delete(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
