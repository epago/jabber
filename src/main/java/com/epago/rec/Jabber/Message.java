package com.epago.rec.Jabber;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {


    LocalDateTime postingDate;
    @ManyToOne
    User creator;
    @Id
    @GeneratedValue
    private Long id;
    private String value;

    public Message(String value, LocalDateTime postingDate, User creator) {
        this.value = value;
        this.postingDate = postingDate;
        this.creator = creator;
    }
}
