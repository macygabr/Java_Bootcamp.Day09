package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;

@Configuration
@ComponentScan("edu.school21.sockets")
@PropertySource("classpath:db.properties")
public class SocketsApplicationConfig {
    @Value("${db.url}")
    private String DB_URL;
    @Value("${db.user}")
    private String DB_USER;
    @Value("${db.password}")
    private String DB_PASSWD;
    @Value("${db.driver.name}")
    private String DB_DRIVER_NAME;

    @Bean(name = "HikariDataSource")
    @Scope("singleton")
    public DataSource initHikariDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(DB_URL);
        hikariConfig.setUsername(DB_USER);
        hikariConfig.setPassword(DB_PASSWD);
        hikariConfig.setDriverClassName(DB_DRIVER_NAME);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        String sql = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), password VARCHAR(255))";
        try {
            dataSource.getConnection().createStatement().execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}