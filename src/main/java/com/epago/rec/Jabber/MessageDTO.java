package com.epago.rec.Jabber;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MessageDTO implements Serializable {

    String postingDate;
    String creator;
    private Long id;
    private String value;

    public MessageDTO(String postingDate, String creator, Long id, String value) {
        this.postingDate = postingDate;
        this.creator = creator;
        this.id = id;
        this.value = value;
    }
}
