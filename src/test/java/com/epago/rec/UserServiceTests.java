package com.epago.rec;

import com.epago.rec.message.MessageRepository;
import com.epago.rec.user.User;
import com.epago.rec.user.UserRepository;
import com.epago.rec.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    private final static String USERNAME1 = "username1";
    private final static String USERNAME2 = "username2";

    @Autowired
    UserService userService;

    @MockBean
    private MessageRepository messageRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddValid() throws Exception {
        when(userRepository.findByUsername(USERNAME1))
                .thenReturn(Optional.of(new User(USERNAME1)));
        when(userRepository.findByUsername(USERNAME2))
                .thenReturn(Optional.of(new User(USERNAME2)));
        userService.follow(USERNAME1, USERNAME2);

    }

    @Test(expected = JabberException.class)
    public void testAddInValid1() throws Exception {
        when(userRepository.findByUsername(USERNAME1))
                .thenReturn(Optional.empty());
        userService.follow(USERNAME1, USERNAME2);

    }

    @Test(expected = JabberException.class)
    public void testAddInValid2() throws Exception {
        when(userRepository.findByUsername(USERNAME1))
                .thenReturn(Optional.of(new User(USERNAME1)));
        when(userRepository.findByUsername(USERNAME2))
                .thenReturn(Optional.empty());
        userService.follow(USERNAME1, USERNAME2);

    }
}
