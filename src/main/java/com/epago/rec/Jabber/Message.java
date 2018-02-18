package com.epago.rec.Jabber;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Message {


    Date postingDate;
    @ManyToOne
    User creator;
    @Id
    @GeneratedValue
    private Long id;
    private String value;

    public Message(String value, Date postingDate, User creator) {
        this.value = value;
        this.postingDate = postingDate;
        this.creator = creator;
    }
}
