package dao;

import model.Location;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
