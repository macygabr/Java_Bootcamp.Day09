package edu.school21.sockets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component("UsersServiceImpl")
public class UsersServiceImpl implements UsersService {
    @Autowired
    @Qualifier("UsersRepositoryImpl")
    private UsersRepository usersRepository;

    public UsersServiceImpl() {
        this.usersRepository = null;
    }

    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void signUp(String login, String pass) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(usersRepository.findByName(login).isPresent()) throw new RuntimeException("User with this login already exists");
        usersRepository.save(new User(login, passwordEncoder.encode(pass)));
    }
}
