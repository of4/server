package dao;

import model.Location;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LocationDao {

    @Autowired
    SessionFactory sessionFactory;

    public void create(Location location) {
        sessionFactory.getCurrentSession().save(location);
    }

    public void update(Location location) {
        sessionFactory.getCurrentSession().update(location);
    }

    public void delete(Location location) {
        sessionFactory.getCurrentSession().delete(location);
    }

    public List<Location> getAllLocations() {
        return sessionFactory.getCurrentSession().createQuery("FROM Location ").list();
    }
}
