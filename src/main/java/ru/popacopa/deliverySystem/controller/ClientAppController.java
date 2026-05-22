package ru.popacopa.deliverySystem.controller;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.popacopa.deliverySystem.entity.Booking;
import ru.popacopa.deliverySystem.entity.Client;
import ru.popacopa.deliverySystem.entity.Food;
import ru.popacopa.deliverySystem.entity.Restorant;
import ru.popacopa.deliverySystem.service.BookingService;
import ru.popacopa.deliverySystem.service.ClientService;
import ru.popacopa.deliverySystem.service.FoodService;
import ru.popacopa.deliverySystem.service.RestorantService;

import java.util.ArrayList;
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
    @Autowired
    FoodService foodService;

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

    @GetMapping("/home/{id}/order")
    public String addOrder(@PathVariable("id") Long id,
                           @RequestParam(value="rest") Long restid,
                           Model model) {
        Client client = clientService.findByClientid(id);
        List<Food> food = foodService.findByRestid(restid);
        model.addAttribute("client", client);
        model.addAttribute("food", food);
        return "add_order";
    }

    @PostMapping("/home/{id}/order")
    @Transactional
    public String addtoBase(@PathVariable("id") Long id,
                            @RequestParam(value = "rest", required = true) Long rest,
                            @RequestParam(value = "food", required = true) Long food) {
        Food f = foodService.findById(food);
        List<Food> l = new ArrayList<Food>();
        l.add(f);
        bookingService.addBooking(id, null, rest, null, "готовится", l);
        return "redirect:/client/v1/home/" + id;
    }
}
