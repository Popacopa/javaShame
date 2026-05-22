package ru.popacopa.deliverySystem.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    public Client() {
        this.login = "pavel";
        this.passwd = "password";
        this.lastname = "name";
        this.phone = "phone";
        this.address = "address";
        this.balance = 0;
    }
    public Client(String login,
                  String password,
                  String name,
                  String phone,
                  String address) {
        this.login = login;
        this.passwd = password;
        this.lastname = name;
        this.phone = phone;
        this.balance = 0;
        this.address = address;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="client_id")
    private Long clientid;
    @Column(name="login", unique = true, nullable = false)
    private String login;
    @Column(name="passwd", nullable = false)
    private String passwd;
    @Column(name="lastname", nullable = false)
    private String lastname;
    @Column(name="phone", nullable = false)
    private String phone;
    @Column(name= "balance", nullable = false)
    private Integer balance;
    @Column(name="address", nullable = false)
    private String address;
}
