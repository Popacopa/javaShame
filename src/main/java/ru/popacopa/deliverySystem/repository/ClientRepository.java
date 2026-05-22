package ru.popacopa.deliverySystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.popacopa.deliverySystem.entity.Client;

import java.util.Optional;
import java.util.OptionalInt;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByClientid(Long id);
    Optional<Client> findByLogin(String login);

}
