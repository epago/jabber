package com.epago.rec.api;

import com.epago.rec.message.MessageService;
import com.epago.rec.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{username}")
public class JabberRestController {

    private final MessageService messageService;
    private final UserService userService;


    @Autowired
    JabberRestController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @RequestMapping(path = "/messages", method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String username, @RequestBody MessageDTO messagedto) {

        try {
            messageService.add(username, messagedto.getMessageText());
        } catch (JabberException jabberException) {
            return ResponseEntity.badRequest().body(jabberException.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/followee/{followeeUsername}", method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String username, @PathVariable String followeeUsername) {

        try {
            userService.follow(username, followeeUsername);
        } catch (JabberException jabberException) {
            return ResponseEntity.badRequest().body(jabberException.getMessage());
        }

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "/messages", method = RequestMethod.GET)
    ResponseEntity<?> readMessages(@PathVariable String username) {

        List<MessageDTO> messageDTOList;
        try {
            messageDTOList = messageService.readMessages(username);
        } catch (JabberException jabberException) {
            return ResponseEntity.badRequest().body(jabberException.getMessage());
        }

        return ResponseEntity.ok(messageDTOList);
    }

    @RequestMapping(path = "/followeeMessages", method = RequestMethod.GET)
    ResponseEntity<?> readFolloweeMessages(@PathVariable String username) {
        List<MessageDTO> messageDTOList;
        try {
            messageDTOList = messageService.readFolloweeMessages(username);
        } catch (JabberException jabberException) {
            return ResponseEntity.badRequest().body(jabberException.getMessage());
        }

        return ResponseEntity.ok(messageDTOList);
    }


}
