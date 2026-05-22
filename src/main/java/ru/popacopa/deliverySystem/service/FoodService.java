package ru.popacopa.deliverySystem.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Food;
import ru.popacopa.deliverySystem.repository.FoodRepository;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public List<Food> findById(Long id) {
        return foodRepository.findByRestid(id);
    }
}
