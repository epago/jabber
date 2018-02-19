package com.epago.rec.message;

import com.epago.rec.api.JabberException;
import com.epago.rec.api.MessageDTO;
import com.epago.rec.user.User;
import com.epago.rec.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public void add(String username, String messageText) throws JabberException {
        if (messageText.length() > 140) {
            throw new JabberException("Invalid operation. Message to long.");
        }
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            user = Optional.of(userRepository.saveAndFlush(new User(username)));
        }
        this.messageRepository.saveAndFlush(new Message(messageText, LocalDateTime.now(), user.get()));
    }

    public List<MessageDTO> readMessages(String username) throws JabberException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new JabberException(String.format("User %s not found", username));
        }
        List<Message> messageList = this.messageRepository.findByCreatorOrderByPostingDateDesc(user.get());

        List<MessageDTO> messageDTOList = mapListEntityToDTO(messageList);

        return messageDTOList;
    }

    public List<MessageDTO> readFolloweeMessages(@PathVariable String username) throws JabberException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new JabberException(String.format("User %s not found", username));
        }

        List<Message> messageList = this.messageRepository.
                findByCreatorInOrderByPostingDateDesc(user.get().getFolloweesList());

        List<MessageDTO> messageDTOList = mapListEntityToDTO(messageList);

        return messageDTOList;
    }

    private List<MessageDTO> mapListEntityToDTO(List<Message> messageList) {
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message : messageList) {
            messageDTOList.add(
                    new MessageDTO(
                            message.getPostingDate().toString(),
                            message.getCreator().getUsername(),
                            message.getMessageText()
                    )
            );

        }
        return messageDTOList;
    }

}
