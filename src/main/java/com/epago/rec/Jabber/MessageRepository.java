package com.epago.rec.Jabber;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByCreator(User creator);

    List<Message> findByCreatorIn(List<User> creatorList);
}