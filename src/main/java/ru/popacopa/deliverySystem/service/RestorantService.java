package ru.popacopa.deliverySystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Restorant;
import ru.popacopa.deliverySystem.repository.RestorantRepository;

import java.util.List;

@Service
public class RestorantService {
    @Autowired
    private RestorantRepository restorantRepository;

    public List<Restorant> getRestorants() {
        return restorantRepository.findAll();
    }

    public Restorant findByRestid(Long id) {
        return restorantRepository.findByRestid(id);
    }
}
