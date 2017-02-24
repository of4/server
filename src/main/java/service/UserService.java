package service;

import dao.UserDao;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Transactional
    public void create(User user) {
        userDao.create(user);
    }

    @Transactional
    public User getByEmailAndPassword(String email, String password) {
        return userDao.getUser(email, password);
    }

    @Transactional
    public void delete(User user) {
        userDao.delete(user);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user);
    }

    @Transactional
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Transactional
    public User getById(Long userId) {
        return userDao.getById(userId);
    }

    @Transactional
    public User getUserByToken(String token) {
        return userDao.getUserByToken(token);
    }
}