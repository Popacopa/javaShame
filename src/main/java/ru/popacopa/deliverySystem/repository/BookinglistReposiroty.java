package ru.popacopa.deliverySystem.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.popacopa.deliverySystem.entity.Booking_list;

import java.util.List;


@Repository
public interface BookinglistReposiroty extends JpaRepository<Booking_list, Long> {
    List<Booking_list> findByBookingid(Long bookingid);
}
