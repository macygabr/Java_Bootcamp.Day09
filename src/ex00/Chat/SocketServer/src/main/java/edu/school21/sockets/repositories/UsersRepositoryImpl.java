package edu.school21.sockets.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

import org.springframework.jdbc.core.JdbcTemplate;

import edu.school21.sockets.models.User;
import javax.sql.DataSource;

@Component("UsersRepositoryImpl")
public class UsersRepositoryImpl implements UsersRepository {

    private final DataSource dataSource;
    
    @Autowired
    public UsersRepositoryImpl(@Qualifier("HikariDataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public <T> List<T> findAll() {
        return null;
    }

    @Override
    public <T> void save(T entity) {
        if(!(entity instanceof User)) throw new RuntimeException("entity must be User type");
        User obj = (User)entity;
        try {
            String sql = "INSERT INTO users (name, password) VALUES (?, ?)";
            Class<?> clazz = obj.getClass();
            Field privateFieldName = clazz.getDeclaredField("name");
            Field privateFieldPass = clazz.getDeclaredField("password");
            privateFieldName.setAccessible(true);
            privateFieldPass.setAccessible(true);
            String name = (String) privateFieldName.get(obj);
            String pass = (String) privateFieldPass.get(obj);
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
            jdbcTemplate.update(sql, name, pass);
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT * FROM Users WHERE name = '" + login+"'";
        List<T> users = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User(rs.getLong("id"),rs.getString("name"),rs.getString("password"));
            return (T) user;
        });
        if (users.size() == 0) return Optional.empty();
        return Optional.of(users.get(0));

    }
    
}