package ru.popacopa.deliverySystem.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Booking;
import ru.popacopa.deliverySystem.entity.Food;
import ru.popacopa.deliverySystem.repository.BookingRepository;

import java.util.List;
import java.util.Optional;


@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public Booking addBooking(Long clientid,
                              Long courierid,
                              Long restid,
                              String restname,
                              Long transportid,
                              String status,
                              List<Food> food) {
        Integer total_cost = 0;
        for (Food f: food) {
            total_cost += f.getFoodcost();
        }
        final Booking booking = new Booking(clientid,
                                            courierid,
                                            restid,
                                            restname,
                                            transportid,
                                            status,
                                            total_cost);
        bookingRepository.save(booking);
        return booking;
    }

    public Booking chStatusById(Long id, String status) throws EntityNotFoundException {
        final Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking == null) {throw new  EntityNotFoundException("Booking not found");}
        booking.setStatus(status);
        return booking;
    }

    public Booking findById(Long id) {
        return bookingRepository.findById(id).orElse(null);
    }
    public List<Booking> findByClientid(Long id) {
        return bookingRepository.findByClientid(id);
    }
}
