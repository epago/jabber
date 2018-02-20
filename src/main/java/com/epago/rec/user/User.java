package com.epago.rec.user;

import com.epago.rec.message.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class User {

    String username;
    @OneToMany
    List<User> followeesList;
    @Id
    @GeneratedValue
    private Long id;
    @OneToMany(mappedBy = "creator")
    private Set<Message> messages = new HashSet<>();

    public User(String username) {
        this.username = username;
        this.followeesList = new ArrayList<>();

    }


}
