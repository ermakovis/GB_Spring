package ru.geekbrains.server.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.geekbrains.server.User;
import ru.geekbrains.server.persistance.UserRepository;

import java.sql.SQLException;

@Component("AuthService")
public class AuthServiceJdbcImpl implements AuthService {

    private UserRepository userRepository;

    @Autowired
    public AuthServiceJdbcImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authUser(User user) {
        try {
            User usr = userRepository.findByLogin(user.getLogin());
            return usr.getId() > 0 && usr.getPassword().equals(user.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
