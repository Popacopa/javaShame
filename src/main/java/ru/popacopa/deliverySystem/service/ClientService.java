package ru.popacopa.deliverySystem.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.repository.ClientRepository;

import java.util.Optional;
import java.util.OptionalInt;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    public void addClient(Client client) {
        Client saved = clientRepository.saveAndFlush(client);
    }


    public Client findByClientid(Long id) {
        return clientRepository.findByClientid(id);
    }

    public Optional<Client> findByLogin(String login) {
        Optional<Client> client = clientRepository.findByLogin(login);
        return client;
    }
}
