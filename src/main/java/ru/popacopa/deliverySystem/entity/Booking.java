package ru.popacopa.deliverySystem.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="booking")
public class Booking {

    public Booking() {}
    public Booking (Long clientid,
                    Long courierid,
                    Long restid,
                    Long transportid,
                    String status,
                    Integer book_cost) {
        this.clientid = clientid;
        this.courierid = courierid;
        this.rest_id = restid;
        this.status = status;
        this.transportid = transportid;
        this.bookcost = book_cost;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    Long bookid;
    @Column(name="client_id")
    Long clientid;
    @Column(name="courier_id")
    Long courierid;
    @Column(name="rest_id")
    Long rest_id;
    @Column(name="transport_id")
    Long transportid;
    @Column(name="book_time")
    LocalDateTime booktime;
    String status;  // готовиться, в пути, доставлен, отменен
    @Column(name="bill")
    Integer bookcost;
}
