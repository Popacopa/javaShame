package ru.popacopa.deliverySystem.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="food_id")
    Long foodid;
    @Column(name="rest_id")
    Long restid;
    @Column(name="food_name")
    String foodname;
    @Column(name="bill")
    Integer foodcost;
}
