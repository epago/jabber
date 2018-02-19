package com.epago.rec.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO implements Serializable {

    private String postingDate;
    private String creator;
    private String messageText;

    public MessageDTO(String postingDate, String creator, String value) {
        this.postingDate = postingDate;
        this.creator = creator;
        this.messageText = value;
    }

}
