package edu.school21.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

@Component("UsersServiceImpl")
public class UsersServiceImpl implements UsersService {
    @Autowired
    @Qualifier("UsersRepositoryImpl")
    private UsersRepository usersRepository;

    public UsersServiceImpl(){
        this.usersRepository = null;
    }

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void saveUser(String login, String pass) {
        // usersRepository.save(new User(login, pass));
    }
}
