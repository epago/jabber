package com.epago.rec.message;

import com.epago.rec.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByCreatorOrderByPostingDateDesc(User creator);
    List<Message> findByCreatorInOrderByPostingDateDesc(List<User> creatorList);
}