package com.epago.rec.message;

import com.epago.rec.user.User;
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

    private LocalDateTime postingDate;
    @ManyToOne
    private User creator;
    @Id
    @GeneratedValue
    private Long id;
    private String messageText;

    public Message(String value, LocalDateTime postingDate, User creator) {
        this.messageText = value;
        this.postingDate = postingDate;
        this.creator = creator;
    }
}
