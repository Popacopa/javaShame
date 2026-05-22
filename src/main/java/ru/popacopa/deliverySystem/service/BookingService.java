package ru.popacopa.deliverySystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Booking;
import ru.popacopa.deliverySystem.repository.BookingRepository;

import java.util.List;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public List<Booking> findByClientid(Long id) {
        return bookingRepository.findByClientid(id);
    }
}
