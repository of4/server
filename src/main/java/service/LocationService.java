package service;

import dao.LocationDao;
import model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocationService {

    @Autowired
    LocationDao locationDao;

    @Transactional
    public void create(Location location) {
        locationDao.create(location);
    }

    @Transactional
    public void delete(Location location) {
        locationDao.delete(location);
    }

    @Transactional
    public void update(Location location) {
        locationDao.update(location);
    }
}
