package com.example.server.model;

import com.example.server.model.enums.UserType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    private String contact;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType role;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private HeadQuarters headQuarters;

    public User(String contact, String name, String username, String password) {
        this.contact = contact;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = UserType.PROVIDER;
    }
}
