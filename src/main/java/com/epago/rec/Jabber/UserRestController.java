package com.epago.rec.Jabber;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
        } else {

            return ResponseEntity.badRequest().body("One of users doesn't exist");
        }


        return ResponseEntity.noContent().build();
    }


}
