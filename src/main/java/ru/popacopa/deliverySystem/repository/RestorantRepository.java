package ru.popacopa.deliverySystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.popacopa.deliverySystem.entity.Restorant;

import java.util.List;

@Repository
public interface RestorantRepository extends JpaRepository<Restorant, Integer> {

    Restorant findByRestid(Long id);

    @Query(value = "SELECT * FROM restorants LIMIT 10", nativeQuery = true)
    List<Restorant> findFirst5();

}
