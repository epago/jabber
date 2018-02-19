package com.epago.rec.user;

import com.epago.rec.api.JabberException;
import com.epago.rec.message.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    UserService(MessageRepository messageRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void follow(String username, String followeeUsername) throws JabberException {

        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> userFoloweeOptional = userRepository.findByUsername(followeeUsername);

        if (!userOptional.isPresent()) {
            throw new JabberException(String.format("User %s not found", username));
        } else if (!userFoloweeOptional.isPresent()) {
            throw new JabberException(String.format("User %s not found", followeeUsername));
        } else {
            User user = userOptional.get();
            user.getFolloweesList().add(userFoloweeOptional.get());
            userRepository.saveAndFlush(user);

        }
    }


}
