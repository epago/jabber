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
@RequestMapping("/{username}")
public class MessageRestController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    MessageRestController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }


    @RequestMapping(path ="/messages" ,method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String username, @RequestBody String message) {

        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            user = Optional.of(userRepository.saveAndFlush(new User(username)));
        }
        this.messageRepository.saveAndFlush(new Message(message, new Date(), user.get()));

        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path ="/messages",method = RequestMethod.GET)
    ResponseEntity<?> readMessages(@PathVariable String username) throws JsonProcessingException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<Message> messageList = this.messageRepository.findByCreator(user.get());

        List<MessageDTO> messageDTOList = mapListEntityToDTO(messageList);

        return ResponseEntity.ok(messageDTOList);
    }

    @RequestMapping(path ="/followeeMessages",method = RequestMethod.GET)
    ResponseEntity<?> readFolloweeMessages(@PathVariable String username) throws JsonProcessingException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        List<Message> messageList = this.messageRepository.findByCreatorIn(user.get().getFolloweesList());

        List<MessageDTO> messageDTOList = mapListEntityToDTO(messageList);

        return ResponseEntity.ok(messageDTOList);
    }

    private List<MessageDTO> mapListEntityToDTO(List<Message> messageList){
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message : messageList) {
            messageDTOList.add(
                    new MessageDTO(
                            message.getPostingDate().toString(),
                            message.getCreator().getUsername(),
                            message.getId(),
                            message.getValue()
                    )
            );

        }
        return messageDTOList;
    }

}
