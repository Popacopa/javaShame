package ru.popacopa.deliverySystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.popacopa.deliverySystem.entity.Restorant;

import java.util.List;

@Repository
public interface RestorantRepository extends JpaRepository<Restorant, Integer> {

}
