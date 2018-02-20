package com.epago.rec;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JabberException extends Exception {
    private String message;
}
