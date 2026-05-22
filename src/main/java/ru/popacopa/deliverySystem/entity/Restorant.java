package ru.popacopa.deliverySystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="restorants")
public class Restorant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="rest_id")
    private Long restid;
    @Column(name="rest_name")
    private String restname;
    private String address;
}
