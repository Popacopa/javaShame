package ru.popacopa.deliverySystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "courier")
public class Courier {

    public Courier() {}
    public Courier(String login,
                   String password,
                   String name,
                   String phone) {
        this.login = login;
        this.lastname = name;
        this.passwd = password;
        this.phone = phone;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courier_id;
    @Column(nullable = false, unique = true, length = 20)
    private String login;
    @Column(nullable = false, length = 20)
    private String lastname;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false, length = 255)
    private String passwd;
}

