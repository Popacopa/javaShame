package ru.popacopa.deliverySystem.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.popacopa.deliverySystem.repository.BookinglistReposiroty;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="booking_list")
public class Booking_list {


    public Booking_list (Long bookingid, Long foodid) {
        this.bookingid=bookingid;
        this.foodid=foodid;
    };
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long bookinglistid;
    @Column(name="booking_id")
    Long bookingid;
    @Column(name="food_id")
    Long foodid;
}
