package ru.popacopa.deliverySystem.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.popacopa.deliverySystem.entity.Booking;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.entity.Restorant;
import ru.popacopa.deliverySystem.service.BookingService;
import ru.popacopa.deliverySystem.service.ClientService;
import ru.popacopa.deliverySystem.service.RestorantService;

import java.util.List;

@Controller
@RequestMapping("/client/v1")
public class ClientAppController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RestorantService restorantService;
    @Autowired
    private BookingService bookingService;

    @GetMapping("/login")
    public String client_login() {
        return "client_login";
    }

    @GetMapping("/registration")
    public String client_registration() {
        return "client_registration";
    }

    @PostMapping("/registration")
    public String addClient(@RequestParam String login,
                            @RequestParam String password,
                            @RequestParam String name,
                            @RequestParam String phone,
                            @RequestParam String address) {

        final String passwd = passwordEncoder.encode(password);
        final Client client = new Client(login, passwd, name, phone, address);
        clientService.addClient(client);
        return "redirect:/client_login";
    }

    @GetMapping("/home/{id}")
    public String access(@PathVariable Long id, Model model) {
        Client client = clientService.findByClientid(id);
        List<Booking> bookinglist = bookingService.findByClientid(id);
        List<Restorant> restorants = restorantService.getRestorants();
        model.addAttribute("client", client);
        model.addAttribute("restorants",  restorants);
        model.addAttribute("bookinglist", bookinglist);
        model.addAttribute("id", id);
        return "client_home";
    }

}
