package ru.popacopa.deliverySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.popacopa.deliverySystem.entity.Courier;

import java.util.Optional;


@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {

    Optional<Courier> findByLogin(String login);
}
