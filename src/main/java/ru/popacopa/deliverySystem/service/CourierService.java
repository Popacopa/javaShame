package ru.popacopa.deliverySystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.entity.Courier;
import ru.popacopa.deliverySystem.repository.ClientRepository;
import ru.popacopa.deliverySystem.repository.CourierRepository;

import java.util.Optional;


@Service
@Transactional
public class CourierService {
    @Autowired
    private CourierRepository courierRepository;

    public Optional<Courier> findByLogin(String login) {
        return courierRepository.findByLogin(login);
    }

    public Optional<Courier> findByCourierid(Long id) {
        return courierRepository.findByCourierid(id);
    }

    public Courier addCourier(Courier courier) {
        courierRepository.save(courier);
        return courier;
    }
}
