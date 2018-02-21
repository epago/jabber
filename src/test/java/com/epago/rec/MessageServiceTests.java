package com.epago.rec;

import com.epago.rec.api.MessageDTO;
import com.epago.rec.message.Message;
import com.epago.rec.message.MessageRepository;
import com.epago.rec.message.MessageService;
import com.epago.rec.user.User;
import com.epago.rec.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTests {

    private final static String USERNAME = "username";
    private final static String SHORT_MESSAGE = "First example message. ";
    private final static String LONG_MESSAGE = "1234567890123456789012345678901234567890" +
            "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
            "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
            "12345678901234567890123456789012345678901234567890123456789012345678901234567890";

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddValid() throws Exception {
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(new User(USERNAME)));
        messageService.add(USERNAME, SHORT_MESSAGE);

    }

    @Test(expected = JabberException.class)
    public void testAddToLong() throws Exception {

        messageService.add(USERNAME, LONG_MESSAGE);

    }

    @Test
    public void testReadMessageValid() throws Exception {
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(new User(USERNAME)));
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(SHORT_MESSAGE, new User(USERNAME)));
        when(messageRepository.findByCreatorOrderByPostingDateDesc(new User(Mockito.anyString())))
                .thenReturn(messageList);
        List<MessageDTO> messageDTOList = messageService.readMessages(USERNAME);
        assertEquals(messageDTOList.get(0).getMessageText(), messageList.get(0).getMessageText());
        assertEquals(messageDTOList.get(0).getCreator(), messageList.get(0).getCreator().getUsername());
    }

    @Test(expected = JabberException.class)
    public void testReadMessageInValid() throws Exception {
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());
        messageService.readMessages(USERNAME);

    }

    @Test
    public void testreadFolloweeMessagesValid() throws Exception {
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(new User(USERNAME)));
        List<Message> messageList = new ArrayList<Message>();
        messageList.add(new Message(SHORT_MESSAGE, new User(USERNAME)));
        List<User> userList = new ArrayList<User>();
        userList.add(new User(Mockito.anyString()));
        when(messageRepository.findByCreatorInOrderByPostingDateDesc(userList))
                .thenReturn(messageList);
        List<MessageDTO> messageDTOList = messageService.readFolloweeMessages(USERNAME);
        assertEquals(messageDTOList.get(0).getMessageText(), messageList.get(0).getMessageText());
        assertEquals(messageDTOList.get(0).getCreator(), messageList.get(0).getCreator().getUsername());
    }

    @Test(expected = JabberException.class)
    public void testreadFolloweeMessagesInValid() throws Exception {
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.empty());
        messageService.readFolloweeMessages(USERNAME);

    }

}
