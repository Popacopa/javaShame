package ru.popacopa.deliverySystem.service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Booking_list;
import ru.popacopa.deliverySystem.repository.BookinglistReposiroty;

import java.util.List;

@Service
public class BookingListService {

    @Autowired
    private BookinglistReposiroty bookinglistReposiroty;

    @Transactional
    public Booking_list add(Long bookingid, Long foodid) {
        Booking_list  bookinglist = new Booking_list(bookingid, foodid);
        bookinglistReposiroty.save(bookinglist);
        return bookinglist;
    }

    @Transactional
    public List<Booking_list> findBybookingid(Long bookingid) {
        return bookinglistReposiroty.findByBookingid(bookingid);
    }
}
