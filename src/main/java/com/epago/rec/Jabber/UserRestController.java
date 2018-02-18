package com.epago.rec.Jabber;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/{username}/followee/{followeeUsername}")
public class UserRestController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    UserRestController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String username, @PathVariable String followeeUsername) {

        Optional<User> userOptional = userRepository.findByUsername(username);
        Optional<User> userFoloweeOptional = userRepository.findByUsername(followeeUsername);

        if (userOptional.isPresent() && userFoloweeOptional.isPresent()) {
            User user = userOptional.get();
            user.getFolloweesList().add(userFoloweeOptional.get());
            userRepository.saveAndFlush(user);
        }else{
            ResponseEntity.badRequest();
        }


        return ResponseEntity.noContent().build();
    }
    

}
