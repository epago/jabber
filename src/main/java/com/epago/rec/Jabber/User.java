package com.epago.rec.Jabber;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    String username;
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "creator")
    private Set<Message> messages = new HashSet<>();

    @OneToMany
    List<User> followeesList;

    public User(String username) {
        this.username = username;

    }


}
