package ru.popacopa.deliverySystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ru.popacopa.deliverySystem.entity.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {

    List<Food> findByRestid(Long id);
    Food findByFoodid(Long id);
}
