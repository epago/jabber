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

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTests {

	@Autowired
	private MessageService messageService;

	@MockBean
	private MessageRepository messageRepository;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void testAddValid()  throws Exception{
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(new User("username")));
		messageService.add("username", "First example message ");

	}

	@Test(expected = JabberException.class)
	public void testAddToLong()  throws Exception{

		messageService.add("username", "1234567890123456789012345678901234567890" +
				"12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
				"12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
				"12345678901234567890123456789012345678901234567890123456789012345678901234567890");

	}

	@Test
	public void testReadMessageValid()  throws Exception{
        when(userRepository.findByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(new User("username")));
		List<Message> messageList = new ArrayList<Message>();
		messageList.add(new Message("message1", new User("user1")));
		when(messageRepository.findByCreatorOrderByPostingDateDesc(new User(Mockito.anyString())))
				.thenReturn(messageList );
		List<MessageDTO> messageDTOList = messageService.readMessages("username");
        assertEquals(messageDTOList.get(0).getMessageText(), messageList.get(0).getMessageText());
        assertEquals(messageDTOList.get(0).getCreator(), messageList.get(0).getCreator().getUsername());
	}



}
