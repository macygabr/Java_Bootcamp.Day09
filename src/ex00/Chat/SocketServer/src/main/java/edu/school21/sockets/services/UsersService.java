package edu.school21.sockets.services;

import org.springframework.stereotype.Component;

public interface UsersService {
    void signUp(String login, String pass);
}
