package edu.school21.sockets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.school21.sockets.models.User;
import javax.sql.DataSource;

@Component("UsersRepositoryImpl")
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    @Qualifier("HikariDataSource")
    private final DataSource dataSource;

    public UsersRepositoryImpl() {
        this.dataSource = null;
    }
    
    public UsersRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> List<T> findAll() {
        return null;
    }

    @Override
    public <T> void save(T entity) {
        if(!(entity instanceof User)) throw new RuntimeException("entity must be User type");

        User user = (User) entity;
        String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
        try {
            dataSource.getConnection().prepareStatement(sql).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void update(T entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public <T> T findById(long id) {
        return null;
    }

    @Override
    public <T> Optional<T> findByName(String login) {
        return null;
    }
    
}